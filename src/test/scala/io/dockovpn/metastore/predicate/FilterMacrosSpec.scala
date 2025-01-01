package io.dockovpn.metastore.predicate

import io.dockovpn.metastore.TestData.ComplexTestRecord
import io.dockovpn.metastore.predicate.Implicits.{OptWrapper, TimestampWrapper}
import io.dockovpn.metastore.predicate.Predicates.{CombPredicate, FieldPredicate, Predicate, PredicateBool, PredicateOps}
import io.dockovpn.metastore.store.AbstractStore
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.Future

class FilterMacrosSpec extends AnyWordSpec
  with ScalaFutures
  with BeforeAndAfter
  with MockFactory {
  
  final case class DummyObject(id: String, value: Int)
  
  trait ApplyPredicateFilter {
    def filter(predicate: Predicate): Future[Seq[ComplexTestRecord]]
  }
  
  class DummyStore(applyFilter: ApplyPredicateFilter) extends AbstractStore[ComplexTestRecord] {
    
    override def contains(k: String): Future[Boolean] = ???
    
    override def filter(predicate: Predicate): Future[Seq[ComplexTestRecord]] = applyFilter.filter(predicate)
    
    override def get(k: String): Future[Option[ComplexTestRecord]] = ???
    
    override def put(k: String, v: ComplexTestRecord): Future[Unit] = ???
    
    override def update(k: String, v: ComplexTestRecord): Future[Unit] = ???
    
    override def getAll(): Future[Seq[ComplexTestRecord]] = ???
  }
  
  private def dummyStore(predicate: Predicate): AbstractStore[ComplexTestRecord] = {
    val applyPredicateFilter = mock[ApplyPredicateFilter]
    (applyPredicateFilter.filter _).expects(predicate).returns(Future.successful(Seq.empty[ComplexTestRecord]))
    
    new DummyStore(applyPredicateFilter)
  }
  
  "FilterMacros" should {
    "generate correct Predicate" when {
      "single bool clause" when {
        "expression is ==" when {
          "filter(_.intValue == 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.Eq, 1)
            dummyStore(expected).filter(_.intValue == 1)
          }
          "filter(_.longValue == 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.Eq, 1)
            dummyStore(expected).filter(_.longValue == 1)
          }
          "filter(_.stringValue == \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.Eq, "example")
            dummyStore(expected).filter(_.stringValue == "example")
          }
          "filter(_.timestampValue == Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.Eq, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue == Timestamp.from(instant))
          }
          "filter(_.optIntValue == None)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Eq, None)
            dummyStore(expected).filter(_.optIntValue == None)
          }
          "filter(_.optIntValue == Some(1))" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Eq, Some(1))
            dummyStore(expected).filter(_.optIntValue == Some(1))
          }
          "filter(_.optLongValue == None)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Eq, None)
            dummyStore(expected).filter(_.optLongValue == None)
          }
          "filter(_.optLongValue == Some(1L))" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Eq, Some(1))
            dummyStore(expected).filter(_.optLongValue == Some(1L))
          }
          "filter(_.optStringValue == None)" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Eq, None)
            dummyStore(expected).filter(_.optStringValue == None)
          }
          "filter(_.optLongValue == Some(\"example\"))" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Eq, Some("example"))
            dummyStore(expected).filter(_.optStringValue == Some("example"))
          }
          "filter(_.optTimestampValue == None)" in {
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Eq, None)
            dummyStore(expected).filter(_.optTimestampValue == None)
          }
          "filter(_.optTimestampValue == Some(Timestamp.from(instant)))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Eq, Some(Timestamp.from(instant)))
            dummyStore(expected).filter(_.optTimestampValue == Some(Timestamp.from(instant)))
          }
        }
        "expression is !=" when {
          "filter(_.intValue != 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.Neq, 1)
            dummyStore(expected).filter(_.intValue != 1)
          }
          "filter(_.longValue != 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.Neq, 1)
            dummyStore(expected).filter(_.longValue != 1)
          }
          "filter(_.stringValue != \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.Neq, "example")
            dummyStore(expected).filter(_.stringValue != "example")
          }
          "filter(_.timestampValue != Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.Neq, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue != Timestamp.from(instant))
          }
          "filter(_.optIntValue != None)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Neq, None)
            dummyStore(expected).filter(_.optIntValue != None)
          }
          "filter(_.optIntValue != Some(1))" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Neq, Some(1))
            dummyStore(expected).filter(_.optIntValue != Some(1))
          }
          "filter(_.optLongValue != None)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Neq, None)
            dummyStore(expected).filter(_.optLongValue != None)
          }
          "filter(_.optLongValue != Some(1L))" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Neq, Some(1))
            dummyStore(expected).filter(_.optLongValue != Some(1L))
          }
          "filter(_.optStringValue != None)" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Neq, None)
            dummyStore(expected).filter(_.optStringValue != None)
          }
          "filter(_.optLongValue != Some(\"example\"))" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Neq, Some("example"))
            dummyStore(expected).filter(_.optStringValue != Some("example"))
          }
          "filter(_.optTimestampValue != None)" in {
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Neq, None)
            dummyStore(expected).filter(_.optTimestampValue != None)
          }
          "filter(_.optTimestampValue != Some(Timestamp.from(instant)))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Neq, Some(Timestamp.from(instant)))
            dummyStore(expected).filter(_.optTimestampValue != Some(Timestamp.from(instant)))
          }
        }
        "expression is >" when {
          "filter(_.intValue > 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.Gt, 1)
            dummyStore(expected).filter(_.intValue > 1)
          }
          "filter(_.longValue > 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.Gt, 1L)
            dummyStore(expected).filter(_.longValue > 1L)
          }
          "filter(_.stringValue > \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.Gt, "example")
            dummyStore(expected).filter(_.stringValue > "example")
          }
          "filter(_.timestampValue > Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.Gt, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue > Timestamp.from(instant))
          }
          "filter(_.optIntValue > 1)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Gt, 1)
            dummyStore(expected).filter(_.optIntValue > 1)
          }
          "filter(_.optLongValue > 1)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Gt, 1L)
            dummyStore(expected).filter(_.optLongValue > 1L)
          }
          "filter(_.optStringValue > \"example\")" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Gt, "example")
            dummyStore(expected).filter(_.optStringValue > "example")
          }
          "filter(_.optTimestampValue > Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Gt, Timestamp.from(instant))
            dummyStore(expected).filter(_.optTimestampValue > Timestamp.from(instant))
          }
        }
        "expression is >=" when {
          "filter(_.intValue >= 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.GtE, 1)
            dummyStore(expected).filter(_.intValue >= 1)
          }
          "filter(_.longValue >= 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.GtE, 1L)
            dummyStore(expected).filter(_.longValue >= 1L)
          }
          "filter(_.stringValue >= \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.GtE, "example")
            dummyStore(expected).filter(_.stringValue >= "example")
          }
          "filter(_.timestampValue >= Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.GtE, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue >= Timestamp.from(instant))
          }
          "filter(_.optIntValue >= 1)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.GtE, 1)
            dummyStore(expected).filter(_.optIntValue >= 1)
          }
          "filter(_.optLongValue >= 1)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.GtE, 1L)
            dummyStore(expected).filter(_.optLongValue >= 1L)
          }
          "filter(_.optStringValue >= \"example\")" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.GtE, "example")
            dummyStore(expected).filter(_.optStringValue >= "example")
          }
          "filter(_.optTimestampValue >= Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.GtE, Timestamp.from(instant))
            dummyStore(expected).filter(_.optTimestampValue >= Timestamp.from(instant))
          }
        }
        "expression is <" when {
          "filter(_.intValue < 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.Lt, 1)
            dummyStore(expected).filter(_.intValue < 1)
          }
          "filter(_.longValue < 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.Lt, 1L)
            dummyStore(expected).filter(_.longValue < 1L)
          }
          "filter(_.stringValue < \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.Lt, "example")
            dummyStore(expected).filter(_.stringValue < "example")
          }
          "filter(_.timestampValue < Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.Lt, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue < Timestamp.from(instant))
          }
          "filter(_.optIntValue < 1)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.Lt, 1)
            dummyStore(expected).filter(_.optIntValue < 1)
          }
          "filter(_.optLongValue < 1)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.Lt, 1L)
            dummyStore(expected).filter(_.optLongValue < 1L)
          }
          "filter(_.optStringValue < \"example\")" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.Lt, "example")
            dummyStore(expected).filter(_.optStringValue < "example")
          }
          "filter(_.optTimestampValue < Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.Lt, Timestamp.from(instant))
            dummyStore(expected).filter(_.optTimestampValue < Timestamp.from(instant))
          }
        }
        "expression is <=" when {
          "filter(_.intValue <= 1)" in {
            val expected = FieldPredicate("intValue", PredicateOps.LtE, 1)
            dummyStore(expected).filter(_.intValue <= 1)
          }
          "filter(_.longValue <= 1)" in {
            val expected = FieldPredicate("longValue", PredicateOps.LtE, 1L)
            dummyStore(expected).filter(_.longValue <= 1L)
          }
          "filter(_.stringValue <= \"example\")" in {
            val expected = FieldPredicate("stringValue", PredicateOps.LtE, "example")
            dummyStore(expected).filter(_.stringValue <= "example")
          }
          "filter(_.timestampValue <= Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("timestampValue", PredicateOps.LtE, Timestamp.from(instant))
            dummyStore(expected).filter(_.timestampValue <= Timestamp.from(instant))
          }
          "filter(_.optIntValue <= 1)" in {
            val expected = FieldPredicate("optIntValue", PredicateOps.LtE, 1)
            dummyStore(expected).filter(_.optIntValue <= 1)
          }
          "filter(_.optLongValue <= 1)" in {
            val expected = FieldPredicate("optLongValue", PredicateOps.LtE, 1L)
            dummyStore(expected).filter(_.optLongValue <= 1L)
          }
          "filter(_.optStringValue <= \"example\")" in {
            val expected = FieldPredicate("optStringValue", PredicateOps.LtE, "example")
            dummyStore(expected).filter(_.optStringValue <= "example")
          }
          "filter(_.optTimestampValue <= Timestamp.from(instant))" in {
            val instant = Instant.now()
            val expected = FieldPredicate("optTimestampValue", PredicateOps.LtE, Timestamp.from(instant))
            dummyStore(expected).filter(_.optTimestampValue <= Timestamp.from(instant))
          }
        }
      }
      "two clauses joined by &&" when {
        "first expression is ==" when {
          "second expression is ==" when {
            "filter(p => p.intValue == 1 && p.intValue == 1)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.intValue == 1)
            }
            "filter(p => p.intValue == 1 && p.longValue == 1L)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("longValue", PredicateOps.Eq, 1L),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.longValue == 1L)
            }
            "filter(p => p.intValue == 1 && p.stringValue == \"example\")" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("stringValue", PredicateOps.Eq, "example"),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.stringValue == "example")
            }
            "filter(p => p.intValue == 1 && p.timestampValue == Timestamp.from(instant))" in {
              val instant = Instant.now()
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("timestampValue", PredicateOps.Eq, Timestamp.from(instant)),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.timestampValue == Timestamp.from(instant))
            }
            "filter(p => p.intValue == 1 && p.optIntValue == None)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optIntValue", PredicateOps.Eq, None),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optIntValue == None)
            }
            "filter(p => p.intValue == 1 && p.optIntValue == Some(1))" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optIntValue", PredicateOps.Eq, Some(1)),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optIntValue == Some(1))
            }
            "filter(p => p.intValue == 1 && p.optLongValue == None)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optLongValue", PredicateOps.Eq, None),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optLongValue == None)
            }
            "filter(p => p.intValue == 1 && p.optLongValue == Some(1L)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optLongValue", PredicateOps.Eq, Some(1L)),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optLongValue == Some(1L))
            }
            "filter(p => p.intValue == 1 && p.optStringValue == None)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optStringValue", PredicateOps.Eq, None),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optStringValue == None)
            }
            "filter(p => p.intValue == 1 && p.optStringValue == Some(\"example\"))" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optStringValue", PredicateOps.Eq, Some("example")),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optStringValue == Some("example"))
            }
            "filter(p => p.intValue == 1 && p.optTimestampValue == None)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optTimestampValue", PredicateOps.Eq, None),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optTimestampValue == None)
            }
            "filter(p => p.intValue == 1 && p.optStringValue == Some(Timestamp.from(instant)))" in {
              val instant = Instant.now()
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optStringValue", PredicateOps.Eq, Some(Timestamp.from(instant))),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optStringValue == Some(Timestamp.from(instant)))
            }
          }
          "second expression is >" when {
            "filter(p => p.intValue == 1 && p.intValue > 1)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("intValue", PredicateOps.Gt, 1),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.intValue > 1)
            }
            "filter(p => p.intValue == 1 && p.longValue > 1L)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("longValue", PredicateOps.Gt, 1L),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.longValue > 1L)
            }
            "filter(p => p.intValue == 1 && p.stringValue > \"example\")" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("stringValue", PredicateOps.Gt, "example"),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.stringValue > "example")
            }
            "filter(p => p.intValue == 1 && p.timestampValue > Timestamp.from(instant))" in {
              val instant = Instant.now()
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("timestampValue", PredicateOps.Gt, Timestamp.from(instant)),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.timestampValue > Timestamp.from(instant))
            }
            "filter(p => p.intValue == 1 && p.optIntValue > 1)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optIntValue", PredicateOps.Gt, 1),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optIntValue > 1)
            }
            "filter(p => p.intValue == 1 && p.optLongValue > 1L)" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optLongValue", PredicateOps.Gt, 1L),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optLongValue > 1L)
            }
            "filter(p => p.intValue == 1 && p.optStringValue > \"example\")" in {
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optStringValue", PredicateOps.Gt, "example"),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optStringValue > "example")
            }
            "filter(p => p.intValue == 1 && p.optTimestampValue > Timestamp.from(instant))" in {
              val instant = Instant.now()
              val expected = CombPredicate(
                FieldPredicate("intValue", PredicateOps.Eq, 1),
                FieldPredicate("optTimestampValue", PredicateOps.Gt, Timestamp.from(instant)),
                PredicateBool.And
              )
              dummyStore(expected).filter(p => p.intValue == 1 && p.optTimestampValue > Timestamp.from(instant))
            }
          }
        }
      }
      "special cases" when {
        "filter(p => p.intValue > 1 && p.intValue == 1)" in {
          val expected = CombPredicate(
            FieldPredicate("intValue", PredicateOps.Gt, 1),
            FieldPredicate("intValue", PredicateOps.Eq, 1),
            PredicateBool.And
          )
          dummyStore(expected).filter(p => p.intValue > 1 && p.intValue == 1)
        }
        "filter(p => p.optIntValue > 1 && p.optIntValue > 1)" in {
          val expected = CombPredicate(
            FieldPredicate("optIntValue", PredicateOps.Gt, 1),
            FieldPredicate("optIntValue", PredicateOps.Gt, 1),
            PredicateBool.And
          )
          dummyStore(expected).filter(p => p.optIntValue > 1 && p.optIntValue > 1)
        }
        "filter(p => p.optStringValue == Some(\"example\") && p.optTimestampValue == Some(Timestamp.from(instant)))" in {
          val instant = Instant.now()
          val expected = CombPredicate(
            FieldPredicate("optStringValue", PredicateOps.Eq, Some("example")),
            FieldPredicate("optTimestampValue", PredicateOps.Eq, Some(Timestamp.from(instant))),
            PredicateBool.And
          )
          dummyStore(expected).filter(p => p.optStringValue == Some("example") && p.optTimestampValue == Some(Timestamp.from(instant)))
        }
        "filter(p => p.timestampValue > Timestamp.from(instant) && p.optTimestampValue > Timestamp.from(instant))" in {
          val instant = Instant.now()
          val expected = CombPredicate(
            FieldPredicate("timestampValue", PredicateOps.Gt, Timestamp.from(instant)),
            FieldPredicate("optTimestampValue", PredicateOps.Gt, Timestamp.from(instant)),
            PredicateBool.And
          )
          dummyStore(expected).filter(p => p.timestampValue > Timestamp.from(instant) && p.optTimestampValue > Timestamp.from(instant))
        }
      }
    }
  }
}
