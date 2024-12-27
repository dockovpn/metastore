/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.store

import io.dockovpn.metastore.predicate.Predicates.Predicate
import io.dockovpn.metastore.util.FilterMacros

import scala.concurrent.Future
import scala.language.experimental.macros

trait AbstractStore[V <: Product] {
  
  def contains(k: String): Future[Boolean]
  
  def filter(predicate: V => Boolean): Future[Seq[V]] = macro FilterMacros.impl[V]
  
  def filter(predicate: Predicate): Future[Seq[V]]
  
  def get(k: String): Future[Option[V]]
  
  def put(k: String, v: V): Future[Unit]
  
  def update(k: String, v: V): Future[Unit]
  
  def getAll(): Future[Seq[V]]
}
