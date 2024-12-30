/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.store

import io.dockovpn.metastore.predicate.Predicates._
import io.dockovpn.metastore.store.MapStore.PredicateWrapper
import io.dockovpn.metastore.util.Types

import scala.collection.concurrent.TrieMap
import scala.concurrent.Future

/**
 * Simple store implementation which has Map behind the scene.
 * This implementation is NOT intended to work in multi-node environment
 * @tparam V
 */
class MapStore[V <: Product] extends AbstractStore[V] {
  
  private val underlying: TrieMap[String, V] = TrieMap.empty
  
  override def filter(predicate: Predicate): Future[Seq[V]] = Future.successful(
    underlying.values.toSeq.filterWithPredicate(predicate).asInstanceOf[Seq[V]]
  )
  
  override def contains(k: String): Future[Boolean] = Future.successful(underlying.contains(k))
  
  override def get(k: String): Future[Option[V]] = Future.successful(underlying.get(k))
  
  override def put(k: String, v: V): Future[Unit] = Future.successful(underlying.addOne(k, v))
  
  override def update(k: String, v: V): Future[Unit] = Future.successful(underlying.update(k, v))
  
  override def getAll(): Future[Seq[V]] = Future.successful(underlying.values.toSeq)
}

object MapStore {
  
  implicit class PredicateWrapper(products: Seq[Product]) {
    
    private def evalFieldPredicate(f: FieldPredicate, product: Product): Boolean = {
      val fieldVal = Types.getFieldToValueMap(product)(f.field)
      
      f.op match {
        case PredicateOps.Eq =>
          fieldVal == f.value
        case PredicateOps.Neq =>
          fieldVal != f.value
        case PredicateOps.Gt =>
          fieldVal match {
            case i: Int => i > f.value.asInstanceOf[Int]
            case i: Long => i > f.value.asInstanceOf[Long]
            case _ => throw new IllegalArgumentException("Can compare using > relationship only between Int ot Long types")
          }
        case PredicateOps.Lt =>
          fieldVal match {
            case i: Int => i < f.value.asInstanceOf[Int]
            case i: Long => i < f.value.asInstanceOf[Long]
            case _ => throw new IllegalArgumentException("Can compare using < relationship only between Int ot Long types")
          }
        case PredicateOps.GtE =>
          fieldVal match {
            case i: Int => i >= f.value.asInstanceOf[Int]
            case i: Long => i >= f.value.asInstanceOf[Long]
            case _ => throw new IllegalArgumentException("Can compare using >= relationship only between Int ot Long types")
          }
        case PredicateOps.LtE =>
          fieldVal match {
            case i: Int => i <= f.value.asInstanceOf[Int]
            case i: Long => i <= f.value.asInstanceOf[Long]
            case _ => throw new IllegalArgumentException("Can compare using <= relationship only between Int ot Long types")
          }
      }
    }
    
    // TODO: convert to tail recursion by applying commutativity law in boolean logic
    private def evalCombPredicate(c: CombPredicate, product: Product): Boolean = (c.left, c.right, c.bop) match {
      case (fl: FieldPredicate, fr: FieldPredicate, bop) => bop match {
        case PredicateBool.And => evalFieldPredicate(fl, product) && evalFieldPredicate(fr, product)
        case PredicateBool.Or => evalFieldPredicate(fl, product) || evalFieldPredicate(fr, product)
      }
      case (fl: FieldPredicate, cr: CombPredicate, bop) => bop match {
        case PredicateBool.And => evalFieldPredicate(fl, product) && evalCombPredicate(cr, product)
        case PredicateBool.Or => evalFieldPredicate(fl, product) || evalCombPredicate(cr, product)
      }
      case (cl: CombPredicate, fr: FieldPredicate, bop) => bop match {
        case PredicateBool.And => evalCombPredicate(cl, product) && evalFieldPredicate(fr, product)
        case PredicateBool.Or => evalCombPredicate(cl, product) || evalFieldPredicate(fr, product)
      }
      case (cl: CombPredicate, cr: CombPredicate, bop) => bop match {
        case PredicateBool.And => evalCombPredicate(cl, product) && evalCombPredicate(cr, product)
        case PredicateBool.Or => evalCombPredicate(cl, product) || evalCombPredicate(cr, product)
      }
    }
    
    private def doesProductConformPredicate(product: Product, predicate: Predicate): Boolean = {
      predicate match {
        case f: FieldPredicate => evalFieldPredicate(f, product)
        case c: CombPredicate => evalCombPredicate(c, product)
      }
    }
    
    def filterWithPredicate(predicate: Predicate): Seq[Product] =
      products.filter(doesProductConformPredicate(_, predicate))
  }
}
