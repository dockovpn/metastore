package io.dockovpn.metastore.container

import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.wait.strategy.Wait

class MariadbContainer extends FixedHostPortGenericContainer[MariadbContainer]("mariadb:latest") {
  
  withFixedExposedPort(3306, 3306)
  withEnv("MARIADB_DATABASE", "metastore")
  withEnv("MARIADB_USER", "test")
  withEnv("MARIADB_PASSWORD", "placeholder")
  withEnv("MARIADB_ROOT_PASSWORD", "placeholder")
  waitingFor(
    Wait.defaultWaitStrategy()
  )
}
