package io.dockovpn.metastore.util

import io.dockovpn.metastore.predicate.Predicates.FieldPredicate

import scala.reflect.macros._
  
object Macros {
  def impl[A](c: whitebox.Context)(predicate: c.Expr[A => Boolean]): c.Tree = {
    import c.universe._
    
    def genMetastorePredicateTree(input: c.Tree): c.Tree = {
      val code = showCode(input)
      val normalized = code.stripPrefix("(").stripSuffix(")")
      val chunks = normalized.split(" => ")
      val valDef = chunks(0).stripPrefix("(").stripSuffix(")")
      val valChunks = valDef.split(": ")
      val valName = valChunks(0)
      //val valType = valChunks(1)
      val normalizedValName = "searchObject"
      val applyDef = chunks(1).replace(valName, normalizedValName)
      
      val clauseExp = s"$normalizedValName\\.(.+?)\\.(==|!=|>|<|>=|<=)\\((.+?)\\).*?".r
      val newCode = clauseExp.findAllIn(applyDef).map { clause =>
        val clauseExp(field, op, comp) = clause
        s"FieldPredicate(\"$field\", \"$op\", $comp)"
      }.mkString(" and ")
      
      println(newCode)
      
      c.parse(newCode)
    }
    
    def compileAndRun(metastorePredicateTree: c.Tree): c.Tree =
      c.parse(s"${c.prefix.tree}.filter($metastorePredicateTree)")
    
    compileAndRun(genMetastorePredicateTree(predicate.tree))
  }
}