package io.dockovpn.metastore.store

import io.dockovpn.metastore.provider.StoreProvider
import io.dockovpn.metastore.store.TestData._
import io.dockovpn.metastore.util.FieldPredicate
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.ExecutionContext.Implicits.global

class MapStoreSpec extends AnyWordSpec
with ScalaFutures
with BeforeAndAfter {
  
  private val mapStoreType = StoreType.MapStoreType
  
  "MapStore" must {
    "put value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(Instant.now()))
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(Instant.now())))
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
    
        opResult should be(())
      }
    }
    "get value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(Instant.now()))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(Instant.now())))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
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
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = 1
        val value = IntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
      
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = 1L
        val value = LongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = "value"
        val value = StringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = Timestamp.from(Instant.now())
        val value = TimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = Some(1)
        val value = OptIntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptIntRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = Some(1L)
        val value = OptLongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptLongRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = Some("value")
        val value = OptStringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptStringRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = Some(Timestamp.from(Instant.now()))
        val value = OptTimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(mapStoreType)
        val key = "key"
        val fieldValue = None
        val value = OptTimestampRecord(id = key, value = fieldValue)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
  
        val getResult = testStore.filter(FieldPredicate("value", "=", fieldValue)).futureValue
        getResult should be(Seq(value))
      }
    }
  }
}
