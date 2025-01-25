package io.dockovpn.metastore.store

import io.dockovpn.metastore.TestData.*
import io.dockovpn.metastore.container.MariadbContainer
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class DBStoreSpec
  extends BasicStore
    with BeforeAndAfter
  with BeforeAndAfterAll {
  
  private var dbContainer: MariadbContainer = _
  
  override def storeType: String = StoreType.DBStoreType
  
  after {
    Await.ready(Queries.cleanTables(), 10.seconds)
  }
  
  override protected def beforeAll(): Unit = {
    dbContainer = new MariadbContainer()
    dbContainer.start()
    
    Await.ready(Queries.initDB(), 20.seconds)
  }
  
  override protected def afterAll(): Unit = {
    dbContainer.close()
  }
}
