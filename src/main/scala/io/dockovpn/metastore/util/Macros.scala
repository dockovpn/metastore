package io.dockovpn.metastore.util

import scala.reflect.macros._
  
object Macros {
  def impl[A](c: whitebox.Context)(predicate: c.Expr[A => Boolean]): c.Tree = {
    import c.universe._
    
    println(showRaw(predicate))
    
    def getMetastoreOp(inputOp: String): String = inputOp match {
      case "$eq$eq" => "\"=\""
      case _ => throw new IllegalArgumentException(s"Unsupported op [$inputOp]")
    }
    
    def getComp(inputComp: c.Tree): String = inputComp match {
      case Ident(TermName(x)) => x
      case Literal(Constant(x)) => x match {
        case s: String => s"\"$x\""
        case _ => x.toString
      }
      case Select(Ident(_), x) =>
        x.toString
      case Apply(
        TypeApply(
          Select(Select(Ident(scala), x), TermName("apply")), List(TypeTree())
        ), List(Literal(Constant(constName)))
      ) =>
        constName match {
          case s: String => s"$x(\"$constName\")"
          case _ => s"$x($constName)"
        }
      
    }
    
    def genMetastorePredicateTree(input: c.Tree): c.Tree = input match {
      case Function(List(ValDef(_, TermName(outerParam), _, _)), block) =>
        block match {
          case Apply(Select(
          Select(Ident(TermName(innerParam)), TermName(fieldName)), TermName(op)
          ),
          List(comp)
          ) if outerParam == innerParam =>
            c.parse(s"FieldPredicate(\"$fieldName\", ${getMetastoreOp(op)}, ${getComp(comp)})")
          
          case _ =>
            throw new IllegalArgumentException("Unrecognized shape of predicate")
        }
      case _ => throw new IllegalArgumentException("Malformed predicate")
    }
    
    def compileAndRun(metastorePredicateTree: c.Tree): c.Tree =
      c.parse(s"${c.prefix.tree}.filter($metastorePredicateTree)")
    
    compileAndRun(genMetastorePredicateTree(predicate.tree))
  }
}