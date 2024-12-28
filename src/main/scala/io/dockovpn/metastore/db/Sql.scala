package io.dockovpn.metastore.db

import io.dockovpn.metastore.predicate.Predicates.{FieldPredicate, Predicate}
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
      case "timestamp" => s"'$maybeNullValue'"
      case "uuid" => s"'$maybeNullValue'"
      case _ => maybeNullValue
    }
  
    val sqlValue = if (maybeQuotedValue == "'null'" || maybeQuotedValue == null)
      "NULL"
    else maybeQuotedValue
    
    sqlValue
  }
  
  def predicateToSql(predicate: Predicate, schema: TableSchema): String = {
    predicate match {
      case FieldPredicate(field, _, value) =>
        val columnName = toCamelCase(field)
        val sqlType = schema(columnName)
        val sqlValue = valueToSqlType(value, sqlType)
        if (sqlValue == "NULL")
          s"$columnName IS NULL"
        else
          s"$columnName = $sqlValue"
      case _ => "1=1" // not implemented
    }
  }
}
