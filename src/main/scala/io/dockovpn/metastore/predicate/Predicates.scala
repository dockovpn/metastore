package io.dockovpn.metastore.predicate

import io.dockovpn.metastore.predicate.Predicates.PredicateBool.{And, Or, PredicateBool}

object Predicates {
  object PredicateBool extends Enumeration {
    type PredicateBool = Value
    
    val And, Or = Value
  }
  
  sealed trait Predicate {
    def and(right: Predicate): Predicate =
      CombPredicate(this, right, And)
      
    def or(right: Predicate): Predicate =
      CombPredicate(this, right, Or)
  }
  case class FieldPredicate(field: String, op: Any, value: Any) extends Predicate
  case class CombPredicate(left: Predicate, right: Predicate, bop: PredicateBool) extends Predicate
}
