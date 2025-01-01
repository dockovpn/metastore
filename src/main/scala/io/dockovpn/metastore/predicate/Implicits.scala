package io.dockovpn.metastore.predicate

import java.sql.Timestamp
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
  
  implicit class TimestampWrapper(x: Timestamp) {
    
    def >(right: Timestamp): Boolean = infixOrderingOps[Timestamp](x) > right
    def >=(right: Timestamp): Boolean = infixOrderingOps[Timestamp](x) >= right
    def <(right: Timestamp): Boolean = infixOrderingOps[Timestamp](x) < right
    def <=(right: Timestamp): Boolean = infixOrderingOps[Timestamp](x) <= right
  }
}
