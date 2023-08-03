/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.provider

import scala.reflect.ClassTag

trait AbstractTableMetadataProvider {
  def getTableMetadata[V](implicit tag: ClassTag[V]): TableMetadata
}
