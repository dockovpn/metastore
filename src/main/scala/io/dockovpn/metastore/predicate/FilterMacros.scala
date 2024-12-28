package io.dockovpn.metastore.predicate

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
      val normalizedValName = if (valName.length > 1 && valName.contains("$")) {
        valName.replace("$", "")
      } else valName
      val applyDef = chunks(1).replace(s"$valName.", s"$normalizedValName.")
        .replace(".&&", " .&&")
        .replace(".||", " .||")
      
      val clauseExp = (normalizedValName + "\\.(.+?)\\.(==|!=|>|<|>=|<=)\\((.+?)\\)(?:\\s|$)").r
      val cobExp = "^\\.(&&|[|]{2})".r
      var applyDefRest = applyDef
      val predicateBuilder = new StringBuilder()
      var iter = 0
      while (applyDefRest.nonEmpty) {
        iter = iter + 1
        clauseExp.findFirstIn(applyDefRest) match {
          case Some(value) =>
            //println(applyDefRest)
            println(value)
            applyDefRest.indexOf(value) match {
              case 0 =>
                applyDefRest = applyDefRest.substring(value.length)
              case 1 => // found expression enclosed in parentheses
                applyDefRest = applyDefRest.substring(value.length + 1)
              case x =>
                throw new RuntimeException(s"Unexpected clause index [$x] while processing expression")
            }
            val clauseExp(field, op, rawComp) = value
            val comp = if (iter > 1) {
              rawComp.stripSuffix(")")
            } else rawComp
            val iCob = cobExp.findFirstIn(applyDefRest) match {
              case Some(value) =>
                applyDefRest = applyDefRest.substring(value.length)
                if (value == ".&&") {
                  " and "
                } else {
                  throw new IllegalArgumentException("Only && boolean operator supported")
                }
              case None => ""
            }
            println(iCob)
            
            predicateBuilder.append(s"FieldPredicate(\"$field\", \"$op\", $comp)$iCob")
          case None => applyDefRest = ""
        }
      }
      c.parse(predicateBuilder.mkString)
    }
    
    def compileAndRun(metastorePredicateTree: c.Tree): c.Tree =
      c.parse(s"import io.dockovpn.metastore.predicate.Predicates._\n${c.prefix.tree}.filter($metastorePredicateTree)")
    
    compileAndRun(genMetastorePredicateTree(predicate.tree))
  }
}