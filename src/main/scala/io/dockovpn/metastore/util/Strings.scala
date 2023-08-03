/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.util

object Strings {
  
  def toCamelCase(field: String): String = {
    def getUpperIndices(field: String): Seq[Int] = {
      val upperIndices = field.toList
        .zipWithIndex
        .filter(p => p._1.isUpper)
      
      upperIndices
        .map(_._2)
        .zipWithIndex
        .map(p => p._1 + p._2)
    }
    
    getUpperIndices(field).foldLeft(field) { (s, p) =>
      val (s1, s2) = s.splitAt(p)
      
      s"${s1}_$s2".toLowerCase
    }
  }
}
