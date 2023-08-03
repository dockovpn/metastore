/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore

import io.dockovpn.metastore.util.Lazy
import slick.jdbc.MySQLProfile

package object db {
  type DBRef = Lazy[MySQLProfile.backend.Database]
}
