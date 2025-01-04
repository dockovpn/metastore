package io.dockovpn.metastore.store

import io.dockovpn.metastore.TestData._
import io.dockovpn.metastore.predicate.Implicits._
import io.dockovpn.metastore.provider.StoreProvider
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

import java.sql.Timestamp
import java.time.{Instant, Period}
import scala.concurrent.ExecutionContext.Implicits.global

trait BasicStore extends  AnyWordSpec
  with ScalaFutures {
  
  def storeType: String
  
  s"$storeType" must {
    "put value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(Instant.now()))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(Instant.now())))
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        
        opResult should be(())
      }
    }
    "get value successfully" when {
      "value is IntRecord" in {
        val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = IntRecord(id = key, value = 1)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is LongRecord" in {
        val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = LongRecord(id = key, value = 1L)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is StringRecord" in {
        val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = StringRecord(id = key, value = "value")
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is TimestampRecord" in {
        val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = TimestampRecord(id = key, value = Timestamp.from(Instant.now()))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptIntRecord(id = key, value = Some(1))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptIntRecord and filed is None" in {
        val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptIntRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptLongRecord(id = key, value = Some(1L))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptLongRecord and filed is None" in {
        val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptLongRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptStringRecord(id = key, value = Some("value"))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptStringRecord and filed is None" in {
        val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptStringRecord(id = key, value = None)
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is Some(_)" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
        val key = "key"
        val value = OptTimestampRecord(id = key, value = Some(Timestamp.from(Instant.now())))
        val opResult: Unit = testStore.put(key, value).futureValue
        opResult should be(())
        
        val getResult = testStore.get(key).futureValue
        getResult.isDefined should be(true)
        getResult.get should be(value)
      }
      "value is OptTimestampRecord and filed is None" in {
        val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
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
      "op is ==" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = 1
          val value = IntRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = 1L
          val value = LongRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = "value"
          val value = StringRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = Timestamp.from(Instant.now())
          val value = TimestampRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptIntRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = Some(1)
          val value = OptIntRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptIntRecord and filed is None" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = None
          val value = OptIntRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptLongRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = Some(1L)
          val value = OptLongRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptLongRecord and filed is None" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = None
          val value = OptLongRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptStringRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = Some("value")
          val value = OptStringRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptStringRecord and filed is None" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = None
          val value = OptStringRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptTimestampRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val ist = Instant.now()
          val fieldValue = Some(Timestamp.from(ist))
          val value = OptTimestampRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
        "value is OptTimestampRecord and filed is None" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key = "key"
          val fieldValue = None
          val value = OptTimestampRecord(id = key, value = fieldValue)
          val opResult: Unit = testStore.put(key, value).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value == fieldValue).futureValue
          getResult should be(Seq(value))
        }
      }
      "op is !=" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1
          val value1 = IntRecord(id = key1, value = fieldValue)
          val value2 = IntRecord(id = key2, value = fieldValue + 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1L
          val value1 = LongRecord(id = key1, value = fieldValue)
          val value2 = LongRecord(id = key2, value = fieldValue + 1L)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = "example"
          val value1 = StringRecord(id = key1, value = fieldValue)
          val value2 = StringRecord(id = key2, value = fieldValue + "1")
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = TimestampRecord(id = key1, value = fieldValue)
          val value2 = TimestampRecord(id = key2, value = Timestamp.from(instant.plus(Period.ofDays(1))))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptIntRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue + 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != Some(fieldValue)).futureValue
          getResult should be(Seq(value3, value2)) //ordering issue
        }
        "value is OptIntRecord and filed is None" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue + 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != None).futureValue
          getResult should be(Seq(value1, value2)) //ordering issue
        }
        "value is OptLongRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue + 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != Some(fieldValue)).futureValue
          getResult should be(Seq(value3, value2)) //ordering issue
        }
        "value is OptLongRecord and filed is None" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue + 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != None).futureValue
          getResult should be(Seq(value1, value2)) //ordering issue
        }
        "value is OptStringRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue + "1"))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != Some(fieldValue)).futureValue
          getResult should be(Seq(value3, value2))
        }
        "value is OptStringRecord and filed is None" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue + "1"))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != None).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptTimestampRecord and filed is Some(_)" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(instant.plus(Period.ofDays(1)))))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != Some(fieldValue)).futureValue
          getResult should be(Seq(value3, value2))
        }
        "value is OptTimestampRecord and filed is None" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(instant.plus(Period.ofDays(1)))))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value != None).futureValue
          getResult should be(Seq(value1, value2))
        }
      }
      "op is >" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1
          val value1 = IntRecord(id = key1, value = fieldValue)
          val value2 = IntRecord(id = key2, value = fieldValue + 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1L
          val value1 = LongRecord(id = key1, value = fieldValue)
          val value2 = LongRecord(id = key2, value = fieldValue + 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = "example"
          val value1 = StringRecord(id = key1, value = fieldValue)
          val value2 = StringRecord(id = key2, value = fieldValue + "1")
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = TimestampRecord(id = key1, value = fieldValue)
          val value2 = TimestampRecord(id = key2, value = Timestamp.from(instant.plus(Period.ofDays(1))))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptIntRecord" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue + 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptLongRecord" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue + 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptStringRecord" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue + "1"))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptTimestampRecord" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(instant.plus(Period.ofDays(1)))))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value > fieldValue).futureValue
          getResult should be(Seq(value2))
        }
      }
      "op is >=" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1
          val value1 = IntRecord(id = key1, value = fieldValue)
          val value2 = IntRecord(id = key2, value = fieldValue + 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 1L
          val value1 = LongRecord(id = key1, value = fieldValue)
          val value2 = LongRecord(id = key2, value = fieldValue + 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = "example"
          val value1 = StringRecord(id = key1, value = fieldValue)
          val value2 = StringRecord(id = key2, value = fieldValue + "1")
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = TimestampRecord(id = key1, value = fieldValue)
          val value2 = TimestampRecord(id = key2, value = Timestamp.from(instant.plus(Period.ofDays(1))))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptIntRecord" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue + 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptLongRecord" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 1L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue + 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptStringRecord" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue + "1"))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptTimestampRecord" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now()
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(instant.plus(Period.ofDays(1)))))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value >= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
      }
      "op is <" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 2
          val value1 = IntRecord(id = key1, value = fieldValue)
          val value2 = IntRecord(id = key2, value = fieldValue - 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 2L
          val value1 = LongRecord(id = key1, value = fieldValue)
          val value2 = LongRecord(id = key2, value = fieldValue - 1L)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = "example1"
          val value1 = StringRecord(id = key1, value = fieldValue)
          val value2 = StringRecord(id = key2, value = fieldValue.stripSuffix("1"))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val instant = Instant.now().plus(Period.ofDays(1))
          val fieldValue = Timestamp.from(instant)
          val value1 = TimestampRecord(id = key1, value = fieldValue)
          val value2 = TimestampRecord(id = key2, value = Timestamp.from(Instant.now()))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptIntRecord" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 2
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue - 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptLongRecord" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 2L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue - 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptStringRecord" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example1"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue.stripSuffix("1")))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
        "value is OptTimestampRecord" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now().plus(Period.ofDays(1))
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(Instant.now())))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value < fieldValue).futureValue
          getResult should be(Seq(value2))
        }
      }
      "op is <=" when {
        "value is IntRecord" in {
          val testStore: AbstractStore[IntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 2
          val value1 = IntRecord(id = key1, value = fieldValue)
          val value2 = IntRecord(id = key2, value = fieldValue - 1)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is LongRecord" in {
          val testStore: AbstractStore[LongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = 2L
          val value1 = LongRecord(id = key1, value = fieldValue)
          val value2 = LongRecord(id = key2, value = fieldValue - 1L)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is StringRecord" in {
          val testStore: AbstractStore[StringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val fieldValue = "example1"
          val value1 = StringRecord(id = key1, value = fieldValue)
          val value2 = StringRecord(id = key2, value = fieldValue.stripSuffix("1"))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is TimestampRecord" in {
          val testStore: AbstractStore[TimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val instant = Instant.now().plus(Period.ofDays(1))
          val fieldValue = Timestamp.from(instant)
          val value1 = TimestampRecord(id = key1, value = fieldValue)
          val value2 = TimestampRecord(id = key2, value = Timestamp.from(Instant.now()))
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptIntRecord" in {
          val testStore: AbstractStore[OptIntRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 2
          val value1 = OptIntRecord(id = key1, value = Some(fieldValue))
          val value2 = OptIntRecord(id = key2, value = Some(fieldValue - 1))
          val value3 = OptIntRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptLongRecord" in {
          val testStore: AbstractStore[OptLongRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = 2L
          val value1 = OptLongRecord(id = key1, value = Some(fieldValue))
          val value2 = OptLongRecord(id = key2, value = Some(fieldValue - 1L))
          val value3 = OptLongRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptStringRecord" in {
          val testStore: AbstractStore[OptStringRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val fieldValue = "example1"
          val value1 = OptStringRecord(id = key1, value = Some(fieldValue))
          val value2 = OptStringRecord(id = key2, value = Some(fieldValue.stripSuffix("1")))
          val value3 = OptStringRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
        "value is OptTimestampRecord" in {
          val testStore: AbstractStore[OptTimestampRecord] = StoreProvider.getStoreByType(storeType)
          val key1 = "key1"
          val key2 = "key2"
          val key3 = "key3"
          val instant = Instant.now().plus(Period.ofDays(1))
          val fieldValue = Timestamp.from(instant)
          val value1 = OptTimestampRecord(id = key1, value = Some(fieldValue))
          val value2 = OptTimestampRecord(id = key2, value = Some(Timestamp.from(Instant.now())))
          val value3 = OptTimestampRecord(id = key3, value = None)
          val opResult: Unit = (for {
            _ <- testStore.put(key1, value1)
            _ <- testStore.put(key2, value2)
            _ <- testStore.put(key3, value3)
          } yield ()).futureValue
          opResult should be(())
          
          val getResult = testStore.filter(_.value <= fieldValue).futureValue
          getResult should be(Seq(value1, value2))
        }
      }
    }
  }
}
