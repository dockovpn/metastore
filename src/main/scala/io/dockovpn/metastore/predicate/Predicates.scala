package io.dockovpn.metastore.predicate

import io.dockovpn.metastore.predicate.Predicates.PredicateBool.PredicateBool

object Predicates {
  object PredicateBool extends Enumeration {
    type PredicateBool = Value
    
    val And, Or = Value
  }
  
  sealed trait Predicate
  case class FieldPredicate(field: String, op: Any, value: Any) extends Predicate
  case class CombPredicate(left: Predicate, right: Predicate, bop: PredicateBool) extends Predicate
}
