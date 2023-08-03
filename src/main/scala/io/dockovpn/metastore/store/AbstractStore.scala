/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.store

import io.dockovpn.metastore.util.Predicate

import scala.concurrent.Future

trait AbstractStore[V] {
  
  def contains(k: String): Future[Boolean]
  
  def filter(predicate: Predicate): Future[Seq[V]]
  
  def get(k: String): Future[Option[V]]
  
  def put(k: String, v: V): Future[Unit]
  
  def update(k: String, v: V): Future[Unit]
}
