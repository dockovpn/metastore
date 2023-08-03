/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.common.provider

import slick.jdbc.GetResult

class TableMetadata(
  val tableName    : String,
  val fieldName    : String,
  private val rconv: GetResult[_]
) {
  object implicits {
    implicit val converter: GetResult[_] = rconv
  }
}
