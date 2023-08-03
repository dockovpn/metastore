/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.common.provider

import scala.reflect.ClassTag

trait AbstractTableMetadataProvider {
  def getTableMetadata[V](implicit tag: ClassTag[V]): TableMetadata
}
