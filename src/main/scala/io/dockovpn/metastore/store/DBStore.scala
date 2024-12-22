/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.store

import io.dockovpn.metastore.db.{DBRef, Queries}
import io.dockovpn.metastore.provider.AbstractTableMetadataProvider
import io.dockovpn.metastore.util.Sql.TableSchema
import io.dockovpn.metastore.util.Strings.toCamelCase
import io.dockovpn.metastore.util.{Predicate, Sql, Types}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

/**
 * Database table backed store implementation
 * @param ec
 * @param dbRef
 * @tparam V
 */
class DBStore[V <: Product](implicit ec: ExecutionContext,
                            metadataProvider: AbstractTableMetadataProvider,
                            tag: ClassTag[V],
                            dbRef: => DBRef) extends AbstractStore[V] {
  
  private val tableMetadata = metadataProvider.getTableMetadata[V]
  
  private lazy val tableSchemaFuture = {
    dbRef.run {
      Queries.getTableSchema(tableMetadata.tableName)
    }.map(_.toMap)
  }
  
  import tableMetadata.implicits._
  
  override def contains(k: String): Future[Boolean] = get(k).map(_.nonEmpty)
  
  override def get(k: String): Future[Option[V]] =
    dbRef.run(
      Queries.getRecordsByKey(k, tableMetadata.tableName, tableMetadata.fieldName)
    ).map(_.headOption.asInstanceOf[Option[V]])
  
  override def put(k: String, v: V): Future[Unit] =
    withSchema { schema =>
      val fieldToValueMap = getColumnNameToSqlValueMap(v, schema)
      val keys = fieldToValueMap.keys.toSeq
      val values = fieldToValueMap.values.toSeq
  
      dbRef.run(
        Queries.insertRecordIntoTable(keys, values, tableMetadata.tableName)
      ).map(_ => ())
    }
  
  override def update(k: String, v: V): Future[Unit] = {
    withSchema { schema =>
      val fieldToValueMap = getColumnNameToSqlValueMap(v, schema)
      val keys = fieldToValueMap.keys.toSeq
      val values = fieldToValueMap.values.toSeq
      
      dbRef.run(
        Queries.updateRecordInTable(k, tableMetadata.fieldName, keys, values, tableMetadata.tableName)
      ).map(_ => ())
    }
  }
  
  override def filter(predicate: Predicate): Future[Seq[V]] =
    withSchema { schema =>
      dbRef.run(
        Queries.filter(predicate, tableMetadata.tableName, schema)
      ).map(_.asInstanceOf[Vector[V]])
    }
  
  override def getAll(): Future[Seq[V]] =
    dbRef.run(
      Queries.getAllRecords(tableMetadata.tableName)
    ).map(_.asInstanceOf[Vector[V]])
  
  private def getColumnNameToSqlValueMap(v: V, schema: Map[String, String]): Map[String, Any] =
    Types.getFieldToValueMap(v)
      .map { p =>
        val kv = (toCamelCase(p._1), p._2)
        val kvt = (kv._1, kv._2, schema(kv._1))
        val (columnName, maybeOptValue, sqlType) = kvt
  
        val sqlValue = Sql.valueToSqlType(maybeOptValue, sqlType)
  
        columnName -> sqlValue
      }
  
  // TODO: consider using fast Future from Akka to avoid scheduling of a Future which result is already available
  private def withSchema[R](f: TableSchema => Future[R]): Future[R] = tableSchemaFuture.flatMap(f)
}
