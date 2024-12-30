package io.dockovpn.metastore.predicate

import scala.math.Ordering.Implicits.infixOrderingOps

object Implicits {
  
  implicit class OptWrapper[A: Ordering](x: Option[A]) {
    
    def >(right: A): Boolean = x match {
      case Some(left) => left > right
      case None => false
    }
    
    def >=(right: A): Boolean = x match {
      case Some(left) => left >= right
      case None => false
    }
    
    def <(right: A): Boolean = x match {
      case Some(left) => left < right
      case None => false
    }
    
    def <=(right: A): Boolean = x match {
      case Some(left) => left <= right
      case None => false
    }
  }
}
