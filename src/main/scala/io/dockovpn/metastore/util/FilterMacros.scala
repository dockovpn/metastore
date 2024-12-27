package io.dockovpn.metastore.util

import scala.reflect.macros._
  
object FilterMacros {
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
      val clauseExp = s"$normalizedValName\\.(.+?)\\.(==|!=|>|<|>=|<=)\\((.+?)\\)(\\)*\\.&&|\\)*\\.[|]{2})*.*?".r
      val newCode = clauseExp.findAllIn(applyDef).map { clause =>
        val clauseExp(field, op, comp, cob) = clause
        val iCob = if (cob == null) {
          ""
        } else if (cob.endsWith(".&&")) {
          " and "
        } else {
          throw new IllegalArgumentException("Only && boolean operator supported")
        }
        s"FieldPredicate(\"$field\", \"$op\", $comp)$iCob"
      }.mkString
      
      println(newCode)
      
      c.parse(newCode)
    }
    
    def compileAndRun(metastorePredicateTree: c.Tree): c.Tree =
      c.parse(s"import io.dockovpn.metastore.predicate.Predicates._\n${c.prefix.tree}.filter($metastorePredicateTree)")
    
    compileAndRun(genMetastorePredicateTree(predicate.tree))
  }
}