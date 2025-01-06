/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.store

import io.dockovpn.metastore.predicate.FilterMacros
import io.dockovpn.metastore.predicate.Predicates.Predicate

import scala.concurrent.Future
import scala.language.experimental.macros

/**
 * The purpose of AbstractStore is to provide a convenient interface to interact with concurrent maps and databases
 * in a unified way and assuring produced results are consistent independently of chosen implementation.
 * The number of methods this interface exposes is deliberately restricted, as the primary goal of this repo was
 * not to implement another Scala SQL engine, but rather provide means to interact with any storage as if it was a
 * simple key-value table.
 * @tparam V any successor of [[Product]], i.e case class.
 */
trait AbstractStore[V <: Product] {
  
  /**
   * Checks whether a record with given key k is present in the store
   * @param k key
   * @return true - if key exists, false - otherwise
   */
  def contains(k: String): Future[Boolean]
  
  /**
   * Filters records in the store using given V => Boolean predicate.
   * Supports ==, !=, >, <, >=, <= for field comparison with a literal or computed value;
   * && and || for combining predicate clauses.<br>
   * Supported field types: [[Int]], [[Long]], [[String]], [[java.sql.Timestamp]], and [[Option]] of these types.
   * Example 1:
   * {{{
   *   _.field > 10
   * }}}<br>
   * Example 2:
   * {{{
   *   p => p.field1 > 10 && p.field2 != None
   * }}}
   * Note: uses macro compiler feature under the hood to build [[Predicate]] AST.
   * @param predicate V => [[Boolean]] expression, where B is a [[Product]]
   * @return Sequence of values, that conform to a given predicate
   */
  def filter(predicate: V => Boolean): Future[Seq[V]] = macro FilterMacros.impl[V]
  
  /**
   * Filters records in the store using given V => Boolean [[Predicate]].<br>
   * Example 1:
   * {{{
   *   FieldPredicate("field", PredicateOps.Gt, 10)
   * }}}
   * Example 2:
   * {{{
   *   CombPredicate(
   *      FieldPredicate("field1", PredicateOps.Gt, 10),
   *      FieldPredicate("field2", PredicateOps.Neq, None),
   *      PredicateBool.And
   *   )
   * }}}
   * @param predicate [[Predicate]]
   * @return Sequence of values, that conform to a given predicate
   */
  @deprecated(since = "5.0.0", message = "Use filter[V: Product](predicate: V => Boolean) version instead")
  def filter(predicate: Predicate): Future[Seq[V]]
  
  /**
   * Optionally retrieves a record V with given key k from the store.
   * @param k key
   * @return Future[Some[V]] - if value exists, Future[None] - otherwise
   */
  def get(k: String): Future[Option[V]]
  
  /**
   * Puts a new record V with given key k into the store.<br>
   * <b>Note:</b> This method is intended to be used with new k only and depending on implementation may throw exception
   * if given key exists.<br>
   * To update existing record, please use `update(k: String, v: V)` method.
   * @param k key
   * @param v value
   * @return Future[Unit]
   */
  def put(k: String, v: V): Future[Unit]
  
  /**
   * Updates existing record in the store by given key k with value v.<br>
   * <b>Note:</b> This method is intended for updating existing record only and depending on implementation
   * may throw exception if a record with given key k doesn't exist.
   * @param k key
   * @param v value
   * @return Future[Unit]
   */
  def update(k: String, v: V): Future[Unit]
  
  /**
   * Retrieves all existing records from the store.
   * <b>Note: </b>This method invocation can return a very big amount of data. Should be used wisely.
   * @return
   */
  def getAll(): Future[Seq[V]]
}
