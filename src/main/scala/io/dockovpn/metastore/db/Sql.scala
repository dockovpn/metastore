package io.dockovpn.metastore.db

import io.dockovpn.metastore.predicate.Predicates.{CombPredicate, FieldPredicate, Predicate, PredicateBool, PredicateOps}
import io.dockovpn.metastore.util.Strings.toCamelCase

object Sql {
  
  type TableSchema = Map[String, String]
  
  def valueToSqlType(maybeOptValue: Any, sqlType: String): Any = {
    // unwrap Option if any
    val maybeNullValue = maybeOptValue match {
      case option: Option[Any] =>
        option.orNull
      case _ => maybeOptValue
    }
  
    // decide which values need to be wrapped in single quotes
    val maybeQuotedValue = sqlType match {
      case a if a startsWith "varchar" => s"'$maybeNullValue'"
      case "tinyint(1)" => if (maybeNullValue.asInstanceOf[Boolean]) 1 else 0
      case t if t startsWith "timestamp" => s"'$maybeNullValue'"
      case "uuid" => s"'$maybeNullValue'"
      case _ => maybeNullValue
    }
  
    val sqlValue = if (maybeQuotedValue == "'null'" || maybeQuotedValue == null)
      "NULL"
    else maybeQuotedValue
    
    sqlValue
  }
  
  private def evalCombPredicate(c: CombPredicate, schema: TableSchema): String = (c.left, c.right, c.bop) match {
    case (fl: FieldPredicate, fr: FieldPredicate, bop) => bop match {
      case PredicateBool.And => evalFieldPredicate(fl, schema) + "\nAND\n" + evalFieldPredicate(fr, schema)
      case PredicateBool.Or => evalFieldPredicate(fl, schema) + "\nOR\n" + evalFieldPredicate(fr, schema)
    }
    case (fl: FieldPredicate, cr: CombPredicate, bop) => bop match {
      case PredicateBool.And => evalFieldPredicate(fl, schema) + "\nAND\n" + evalCombPredicate(cr, schema)
      case PredicateBool.Or => evalFieldPredicate(fl, schema) + "\nOR\n" + evalCombPredicate(cr, schema)
    }
    case (cl: CombPredicate, fr: FieldPredicate, bop) => bop match {
      case PredicateBool.And => evalCombPredicate(cl, schema) + "\nAND\n" + evalFieldPredicate(fr, schema)
      case PredicateBool.Or => evalCombPredicate(cl, schema) + "\nOR\n" + evalFieldPredicate(fr, schema)
    }
    case (cl: CombPredicate, cr: CombPredicate, bop) => bop match {
      case PredicateBool.And => evalCombPredicate(cl, schema) + "\nAND\n" + evalCombPredicate(cr, schema)
      case PredicateBool.Or => evalCombPredicate(cl, schema) + "\nOR\n" + evalCombPredicate(cr, schema)
    }
  }
  
  private def evalFieldPredicate(f: FieldPredicate, schema: TableSchema): String = {
    val columnName = toCamelCase(f.field)
    val sqlType = schema(columnName)
    val sqlValue = valueToSqlType(f.value, sqlType)
    f.op match {
      case PredicateOps.Eq =>
        if (sqlValue == "NULL")
          s"$columnName IS NULL"
        else
          s"$columnName = $sqlValue"
      case PredicateOps.Neq =>
        if (sqlValue == "NULL")
          s"$columnName IS NOT NULL"
        else
          s"$columnName != $sqlValue"
      case PredicateOps.Gt =>
        if (sqlValue == "NULL")
          throw new IllegalArgumentException("Cannot compare with NULL using > relationship")
        else
          s"$columnName > $sqlValue"
      case PredicateOps.Lt =>
        if (sqlValue == "NULL")
          throw new IllegalArgumentException("Cannot compare with NULL using < relationship")
        else
          s"$columnName < $sqlValue"
      case PredicateOps.GtE =>
        if (sqlValue == "NULL")
          throw new IllegalArgumentException("Cannot compare with NULL using >= relationship")
        else
          s"$columnName >= $sqlValue"
      case PredicateOps.LtE =>
        if (sqlValue == "NULL")
          throw new IllegalArgumentException("Cannot compare with NULL using <= relationship")
        else
          s"$columnName <= $sqlValue"
    }
  }
  
  def predicateToSql(predicate: Predicate, schema: TableSchema): String = {
    predicate match {
      case x: FieldPredicate =>
        evalFieldPredicate(x, schema)
      case x: CombPredicate =>
        evalCombPredicate(x, schema)
    }
  }
}
