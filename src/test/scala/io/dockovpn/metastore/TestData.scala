package io.dockovpn.metastore

import com.typesafe.config.ConfigFactory
import io.dockovpn.metastore.db.DBRef
import io.dockovpn.metastore.provider.{AbstractTableMetadataProvider, TableMetadata}
import io.dockovpn.metastore.util.Lazy.lazily
import io.dockovpn.metastore.util.Types.getType
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import java.sql.Timestamp
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

object TestData {
  
  case class IntRecord(id: String, value: Int)
  case class LongRecord(id: String, value: Long)
  case class StringRecord(id: String, value: String)
  case class TimestampRecord(id: String, value: Timestamp)
  case class OptIntRecord(id: String, value: Option[Int])
  case class OptLongRecord(id: String, value: Option[Long])
  case class OptStringRecord(id: String, value: Option[String])
  case class OptTimestampRecord(id: String, value: Option[Timestamp])
  
  case class ComplexTestRecord(
    id                : String,
    intValue          : Int,
    longValue         : Long,
    stringValue       : String,
    timestampValue    : Timestamp,
    optIntValue       : Option[Int],
    optLongValue      : Option[Long],
    optStringValue    : Option[String],
    optTimestampValue : Option[Timestamp]
  )
  
  implicit val metadataProvider: AbstractTableMetadataProvider = new AbstractTableMetadataProvider {
    override def getTableMetadata[V](implicit tag: ClassTag[V]): TableMetadata = {
      val IntRecordClass: String = getType[IntRecord].toString.replace("TestData.", "TestData$")
      val LongRecordClass: String = getType[LongRecord].toString.replace("TestData.", "TestData$")
      val StringRecordClass: String = getType[StringRecord].toString.replace("TestData.", "TestData$")
      val TimestampRecordClass: String = getType[TimestampRecord].toString.replace("TestData.", "TestData$")
      val OptIntRecordClass: String = getType[OptIntRecord].toString.replace("TestData.", "TestData$")
      val OptLongRecordClass: String = getType[OptLongRecord].toString.replace("TestData.", "TestData$")
      val OptStringRecordClass: String = getType[OptStringRecord].toString.replace("TestData.", "TestData$")
      val OptTimestampRecordClass: String = getType[OptTimestampRecord].toString.replace("TestData.", "TestData$")
      val ComplexTestRecordClass: String = getType[ComplexTestRecord].toString.replace("TestData.", "TestData$")
  
      tag.runtimeClass.getName match {
        case IntRecordClass =>
          new TableMetadata(
            tableName = "int_records",
            fieldName = "id",
            rconv = GetResult { r =>
              IntRecord(r.<<, r.<<)
            }
          )
        case LongRecordClass =>
          new TableMetadata(
            tableName = "long_records",
            fieldName = "id",
            rconv = GetResult { r =>
              LongRecord(r.<<, r.<<)
            }
          )
        case StringRecordClass =>
          new TableMetadata(
            tableName = "string_records",
            fieldName = "id",
            rconv = GetResult { r =>
              StringRecord(r.<<, r.<<)
            }
          )
        case TimestampRecordClass =>
          new TableMetadata(
            tableName = "timestamp_records",
            fieldName = "id",
            rconv = GetResult { r =>
              TimestampRecord(r.<<, r.<<)
            }
          )
        case OptIntRecordClass =>
          new TableMetadata(
            tableName = "opt_int_records",
            fieldName = "id",
            rconv = GetResult { r =>
              OptIntRecord(r.<<, r.<<)
            }
          )
        case OptLongRecordClass =>
          new TableMetadata(
            tableName = "opt_long_records",
            fieldName = "id",
            rconv = GetResult { r =>
              OptLongRecord(r.<<, r.<<)
            }
          )
        case OptStringRecordClass =>
          new TableMetadata(
            tableName = "opt_string_records",
            fieldName = "id",
            rconv = GetResult { r =>
              OptStringRecord(r.<<, r.<<)
            }
          )
        case OptTimestampRecordClass =>
          new TableMetadata(
            tableName = "opt_timestamp_records",
            fieldName = "id",
            rconv = GetResult { r =>
              OptTimestampRecord(r.<<, r.<<)
            }
          )
        case ComplexTestRecordClass =>
          new TableMetadata(
            tableName = "complex_test_record",
            fieldName = "id",
            rconv = GetResult { r =>
              ComplexTestRecord(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<)
            }
          )
        case unrecognizedType =>
          throw new IllegalArgumentException(s"Unrecognized type [$unrecognizedType] to get TableMetadata for")
      }
    }
  }
  
  private val cfg = ConfigFactory.load("db-test.conf")
  
  implicit val dbRef: DBRef = lazily { Database.forConfig("slick-mariadb", cfg) }
  
  object Queries {
    def initDB(): Future[Unit] = {
      Future.sequence(List(
        dbRef.run {
          sql"""CREATE TABLE `int_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` int(11) NOT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `long_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` bigint(20) NOT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `string_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` varchar(100) NOT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `timestamp_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` timestamp(6) NOT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `opt_int_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` int(11) DEFAULT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `opt_long_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` bigint(20) DEFAULT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `opt_string_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` varchar(100) DEFAULT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `opt_timestamp_records` (
               |  `id` varchar(100) NOT NULL,
               |  `value` timestamp(6) NULL DEFAULT NULL,
               |  PRIMARY KEY (`id`)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""CREATE TABLE `complex_test_record` (
               |	id varchar(100) NOT NULL,
               |	int_value INT NOT NULL,
               |	long_value BIGINT NOT NULL,
               |	string_value varchar(100) NOT NULL,
               |	timestamp_value TIMESTAMP(6) NOT NULL,
               |	opt_int_value INT NULL,
               |	opt_long_value BIGINT NULL,
               |	opt_string_value varchar(100) NULL,
               |	opt_timestamp_value TIMESTAMP(6) NULL,
               |	CONSTRAINT complex_test_record_PK PRIMARY KEY (id)
               |) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
               |""".stripMargin.as[Unit]
        },
      )).map(_ => ())
    }
    
    def cleanTables(): Future[Unit] = {
      Future.sequence(List(
        dbRef.run {
          sql"""TRUNCATE TABLE int_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE long_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE string_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE timestamp_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE opt_int_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE opt_long_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE opt_string_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE opt_timestamp_records
               |""".stripMargin.as[Unit]
        },
        dbRef.run {
          sql"""TRUNCATE TABLE complex_test_record
               |""".stripMargin.as[Unit]
        },
      )).map(_ => ())
    }
  }
}
