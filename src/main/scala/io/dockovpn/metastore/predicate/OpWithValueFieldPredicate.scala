package io.dockovpn.metastore.predicate

import io.dockovpn.metastore.util.FieldPredicate

class OpWithValueFieldPredicate(field: String, op: String) {
  
  def right(value: Any) = FieldPredicate(field, op, value)
}
