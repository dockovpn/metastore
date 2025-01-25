/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.provider

import io.dockovpn.metastore.db.{DBRef, noopDB}
import io.dockovpn.metastore.store.{AbstractStore, DBStore, MapStore, StoreType}

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

object StoreProvider {
  
  def getStoreByType[V <: Product: ClassTag](storeType: String)
                                  (implicit ec: ExecutionContext,
                                            metadataProvider: AbstractTableMetadataProvider,
                                            DBRef: DBRef = noopDB): AbstractStore[V] =
    storeType match {
      case StoreType.MapStoreType =>
        new MapStore[V]()
        
      case StoreType.DBStoreType =>
        new DBStore[V]
        
      case _ => throw new IllegalArgumentException(s"Unsupported store type [$storeType]")
    }
}
