/*
 * Copyright (c) 2023. Dockovpn Solutions. All Rights Reserved
 */

package io.dockovpn.metastore.util

import java.util.concurrent.atomic.AtomicReference
import scala.language.implicitConversions

object Lazy {
  
  def lazily[A](f: => A): Lazy[A] = new Lazy(f)
  
  implicit def evalLazy[A](l: Lazy[A]): A = l()
  
}

class Lazy[A] private(f: => A) {
  
  private val optionAtomicRef: AtomicReference[Option[A]] = new AtomicReference[Option[A]](None)
  
  def apply(): A = this.synchronized {
    optionAtomicRef.get() match {
      case Some(a) => a
      case None => val a = f; optionAtomicRef.set(Some(a)); a
    }
  }
  
  def isEvaluated: Boolean = optionAtomicRef.get().isDefined
  
}
