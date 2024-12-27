package io.dockovpn.metastore.store

import io.dockovpn.metastore.container.MariadbContainer
import io.dockovpn.metastore.provider.StoreProvider
import io.dockovpn.metastore.store.TestData._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class DBStoreSpec extends AnyWordSpec
  with ScalaFutures
  with BeforeAndAfterAll {
  
  private val dbStoreType = StoreType.DBStoreType
  private val baseInstant = Instant.parse("2023-12-18T21:22:34Z")
  
  override protected def beforeAll(): Unit = {
    val dbContainer = new MariadbContainer()
    dbContainer.start()
    
    Await.ready(Queries.initDB(), 20.seconds)
  }
  
  override protected def afterAll(): Unit = {
    Await.ready(Queries.cleanTables(), 10.seconds)
  }
  
  "DBStore" should {
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
        val key = "keySome"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(baseInstant)))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be (())
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
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
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(baseInstant))
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptIntRecord(id = key, value = Some(1))
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptIntRecord(id = key, value = None)
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptLongRecord(id = key, value = Some(1L))
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptLongRecord(id = key, value = None)
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptStringRecord(id = key, value = Some("value"))
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptStringRecord(id = key, value = None)
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(baseInstant)))
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val value = OptTimestampRecord(id = key, value = None)
        
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
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = 1L
        val value = LongRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = "value"
        val value = StringRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "key"
        val fieldValue = Timestamp.from(baseInstant)
        val value = TimestampRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val fieldValue = Some(1)
        val value = OptIntRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val fieldValue = None
        val value = OptIntRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val fieldValue = Some(1L)
        val value = OptLongRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val fieldValue = None
        val value = OptLongRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val fieldValue = Some("value")
        val value = OptStringRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val fieldValue = None
        val value = OptStringRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keySome"
        val fieldValue = Some(Timestamp.from(baseInstant))
        val value = OptTimestampRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(dbStoreType)
        val key = "keyNone"
        val fieldValue = None
        val value = OptTimestampRecord(id = key, value = fieldValue)
        
        val getResult = testStore.filter(_.value == fieldValue).futureValue
        getResult should be(Seq(value))
      }
    }
  }
}
