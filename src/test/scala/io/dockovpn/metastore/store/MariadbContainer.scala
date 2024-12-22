package io.dockovpn.metastore.store

import io.dockovpn.metastore.store.TestData.Queries
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.wait.strategy.Wait

import java.time.Duration
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class MariadbContainer extends FixedHostPortGenericContainer[MariadbContainer]("mariadb:latest") {
  
  //private val logEntryRegEx = ".*socket: '/run/mysqld/mysqld.sock'  port: 3306  mariadb.org binary distribution.*"
  //private val startupTimeout = Duration.ofSeconds(10)
  
  withFixedExposedPort(3306, 3306)
  withEnv("MARIADB_DATABASE", "metastore")
  withEnv("MARIADB_USER", "test")
  withEnv("MARIADB_PASSWORD", "placeholder")
  withEnv("MARIADB_ROOT_PASSWORD", "placeholder")
  waitingFor(
    Wait.defaultWaitStrategy()
  )
  
  /*def init(): Unit = {
    Await.ready(Queries.initDB(), 10.seconds)
  }*/
}
