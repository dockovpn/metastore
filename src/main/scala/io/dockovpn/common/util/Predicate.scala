/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.common.util

import io.dockovpn.common.util.PredicateBool.PredicateBool

object PredicateBool extends Enumeration {
  type PredicateBool = Value
  
  val And, Or = Value
}

sealed trait Predicate
case class FieldPredicate(field: String, op: Any, value: Any) extends Predicate
case class CombPredicate(left: Predicate, right: Predicate, bop: PredicateBool) extends Predicate
