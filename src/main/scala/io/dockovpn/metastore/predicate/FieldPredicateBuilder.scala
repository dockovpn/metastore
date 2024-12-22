package io.dockovpn.metastore.predicate

class FieldPredicateBuilder(fieldName: String) {
  
  lazy val Eq: OpWithValueFieldPredicate = new OpWithValueFieldPredicate(fieldName, "=")
}
