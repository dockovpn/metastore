/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.util

import scala.reflect.runtime.universe._
import scala.util.Try

object Types {
  
  def getType[A: TypeTag]: Type = typeOf[A]
  
  def getFieldToValueMap[A](v: A): Map[String, Any] = {
    Try (
      v.asInstanceOf[Product]
    ).fold(
      _ => Map.empty,
      prod => {
        prod.productElementNames
          .zip(prod.productIterator)
          .toMap
      }
    )
  }
  
}
