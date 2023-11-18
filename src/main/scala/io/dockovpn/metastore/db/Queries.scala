/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.db

import io.dockovpn.metastore.util.Strings.toCamelCase
import io.dockovpn.metastore.util.{FieldPredicate, Predicate}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._
import slick.sql.SqlStreamingAction

object Queries {
  
  def getRecordsByKey[V](k: String, table: String, field: String)
                        (implicit rconv: GetResult[V]): SqlStreamingAction[Vector[V], V, Effect] =
    sql"""SELECT * FROM #$table
         |WHERE #$field = $k
         |""".stripMargin.as[V]
         
  def filter[V](predicate: Predicate, table: String)(implicit rconv: GetResult[V]): SqlStreamingAction[Vector[V], V, Effect] = {
    val sqlPredicate = predicateToSql(predicate)
    
    sql"""SELECT * FROM #$table
         |WHERE #$sqlPredicate
         |""".stripMargin.as[V]
  }
         
  def insertRecordIntoTable[V](keys: Seq[String], values: Seq[Any], table: String)
                           (implicit rconv: GetResult[V]): SqlStreamingAction[Vector[V], V, Effect] = {
    val keysStr = keys.mkString(", ")
    val valuesStr = values.mkString(", ")
    
    sql"""INSERT INTO #$table (#$keysStr) VALUES(#$valuesStr)""".as[V]
  }
  
  def updateRecordInTable[V](k: String, field: String, keys: Seq[String], values: Seq[Any], table: String)
                              (implicit rconv: GetResult[V]): SqlStreamingAction[Vector[V], V, Effect] = {
    val expression = keys.zip(values).map(e => s"${e._1} = ${e._2}").mkString(", ")
    
    sql"""UPDATE #$table
         |SET #$expression
         |WHERE #$field = $k
         |""".stripMargin.as[V]
  }
  
  def getTableSchema(table: String): SqlStreamingAction[Vector[(String, String)], (String, String), Effect] = {
    sql"""DESCRIBE #$table
       |""".stripMargin.as[(String, String)]
  }
  
  def getAllRecords[V](table: String)(implicit rconv: GetResult[V]): SqlStreamingAction[Vector[V], V, Effect] = {
    sql"""SELECT * FROM #$table
         |""".stripMargin.as[V]
  }
  
  // TODO: Implement Predicate -> SQL materializer
  private def predicateToSql(predicate: Predicate): String = {
    predicate match {
      case FieldPredicate(field, _, value) => s"${toCamelCase(field)} = '$value'"
      case _ => "1=1" // not implemented
    }
  }
}
