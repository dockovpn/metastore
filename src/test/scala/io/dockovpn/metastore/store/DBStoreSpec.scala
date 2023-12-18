package io.dockovpn.metastore.store

import io.dockovpn.metastore.provider.StoreProvider
import io.dockovpn.metastore.store.TestData._
import io.dockovpn.metastore.util.FieldPredicate
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import slick.jdbc.MySQLProfile.api._

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class DBStoreSpec extends AnyWordSpec
  with ScalaFutures
  with BeforeAndAfter {
  
  private val dbStoreType = StoreType.DBStoreType
  private val baseInstant = Instant.parse("2023-12-18T21:22:34Z")
  
  before {
    val f = Future.sequence(List(
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
    ))
    
    Await.ready(f, 10.seconds)
  }
  
  "DBStore" must {
    "put value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(baseInstant))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(baseInstant)))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
    }
    "get value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(baseInstant))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(baseInstant)))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
    }
    "filter value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = 1
        val value = IntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = 1L
        val value = LongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = "value"
        val value = StringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Timestamp.from(baseInstant)
        val value = TimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Some(1)
        val value = OptIntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptIntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Some(1L)
        val value = OptLongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptLongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Some("value")
        val value = OptStringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptStringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Some(Timestamp.from(Instant.now()))
        val value = OptTimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptTimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be (())
        
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
    }
  }
}
