package io.dockovpn.metastore.predicate

import java.sql.Timestamp
import scala.math.Ordering.Implicits.infixOrderingOps

object Implicits {
  
  sealed trait FieldComparable[A] {
    def >(right: A): Boolean
    def >=(right: A): Boolean
    def <(right: A): Boolean
    def <=(right: A): Boolean
  }
  
  implicit class FieldIntWrapper(x: Option[Int]) extends FieldComparable[Int] {
    
    override def >(right: Int): Boolean = x match {
      case Some(left) => left > right
      case None => false
    }
    
    override def >=(right: Int): Boolean = x match {
      case Some(left) => left >= right
      case None => false
    }
    
    override def <(right: Int): Boolean = x match {
      case Some(left) => left < right
      case None => false
    }
    
    override def <=(right: Int): Boolean = x match {
      case Some(left) => left <= right
      case None => false
    }
  }
  
  implicit class FieldLongWrapper(x: Option[Long]) extends FieldComparable[Long] {
    
    override def >(right: Long): Boolean = x match {
      case Some(left) => left > right
      case None => false
    }
    
    override def >=(right: Long): Boolean = x match {
      case Some(left) => left >= right
      case None => false
    }
    
    override def <(right: Long): Boolean = x match {
      case Some(left) => left < right
      case None => false
    }
    
    override def <=(right: Long): Boolean = x match {
      case Some(left) => left <= right
      case None => false
    }
  }
  
  implicit class FieldStringWrapper(x: Option[String]) extends FieldComparable[String] {
    override def >(right: String): Boolean = x match {
      case Some(left) => left > right
      case None => false
    }
    
    override def >=(right: String): Boolean = x match {
      case Some(left) => left >= right
      case None => false
    }
    
    override def <(right: String): Boolean = x match {
      case Some(left) => left < right
      case None => false
    }
    
    override def <=(right: String): Boolean = x match {
      case Some(left) => left <= right
      case None => false
    }
  }
  
  implicit class FieldTimestampWrapper(x: Option[Timestamp]) extends FieldComparable[Timestamp] {
    override def >(right: Timestamp): Boolean = x match {
      case Some(left) => left > right
      case None => false
    }
    
    override def >=(right: Timestamp): Boolean = x match {
      case Some(left) => left >= right
      case None => false
    }
    
    override def <(right: Timestamp): Boolean = x match {
      case Some(left) => left < right
      case None => false
    }
    
    override def <=(right: Timestamp): Boolean = x match {
      case Some(left) => left <= right
      case None => false
    }
  }
}
