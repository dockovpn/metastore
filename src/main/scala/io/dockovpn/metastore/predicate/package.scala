package io.dockovpn.metastore

import io.dockovpn.metastore.predicate.PredicateBool.PredicateBool

package object predicate {
  object PredicateBool extends Enumeration {
    type PredicateBool = Value
    
    val And, Or = Value
  }
  
  sealed trait Predicate
  case class FieldPredicate(field: String, op: Any, value: Any) extends Predicate
  case class CombPredicate(left: Predicate, right: Predicate, bop: PredicateBool) extends Predicate
}
