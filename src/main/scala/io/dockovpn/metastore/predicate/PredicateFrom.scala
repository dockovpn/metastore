package io.dockovpn.metastore.predicate

class PredicateFrom[V <: Product] {
  
  def field(name: String): FieldPredicateBuilder = new FieldPredicateBuilder(name)
}
