/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore

import io.dockovpn.metastore.util.Lazy
import io.dockovpn.metastore.util.Lazy.lazily
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

package object db {
  type DBRef = Lazy[MySQLProfile.backend.Database]
  
  def noopDB: DBRef = lazily { Database.forConfig("") }
}
