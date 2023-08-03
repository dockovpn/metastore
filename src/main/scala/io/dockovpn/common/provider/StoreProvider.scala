/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.common.provider

import io.dockovpn.common.db.DBRef
import io.dockovpn.common.store.{AbstractStore, DBStore, MapStore, StoreType}

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

object StoreProvider {
  
  def getStoreByType[V <: Product](storeType: String)
                                  (implicit ec: ExecutionContext,
                                            DBRef: DBRef,
                                            metadataProvider: AbstractTableMetadataProvider,
                                            tag: ClassTag[V]): AbstractStore[V] =
    storeType match {
      case StoreType.MapStoreType =>
        new MapStore[V]()
        
      case StoreType.DBStoreType =>
        new DBStore[V]
        
      case _ => throw new IllegalArgumentException(s"Unsupported store type [$storeType]")
    }
}
