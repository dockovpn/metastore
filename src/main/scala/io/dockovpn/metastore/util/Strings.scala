/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.util

import scala.util.matching.Regex

object Strings {
  
  def toCamelCase(field: String): String = field.toCamelCase
  
  implicit class StringOps(value: String) {
    
    def toCamelCase: String = replaceWith("[A-Z]".r, (found, _) => s"_${found.toLowerCase}").stripPrefix("_")
    
    def toSnakeCase: String = replaceWith("_([a-z])".r, (found, regex) => {
      val regex(l) = found
      l.capitalize
    })
    
    def replaceWith(pattern: Regex, replaceFunc: (String, Regex) => String): String = {
      val strBuilder = new StringBuilder()
      var valueRest = value
      while (valueRest.nonEmpty) {
        pattern.findFirstIn(valueRest) match {
          case Some(found) =>
            val chunks = valueRest.split(found, 2)
            strBuilder.append(chunks(0))
            strBuilder.append(replaceFunc(found, pattern))
            valueRest = chunks(1)
          case None =>
            strBuilder.append(valueRest)
            valueRest = ""
        }
      }
      strBuilder.mkString
    }
  }
}
