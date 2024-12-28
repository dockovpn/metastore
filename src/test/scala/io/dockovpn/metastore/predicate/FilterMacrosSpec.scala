package io.dockovpn.metastore.predicate

import io.dockovpn.metastore.TestData.ComplexTestRecord
import io.dockovpn.metastore.predicate.Predicates.{FieldPredicate, Predicate}
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
        "filter(_.intValue == 1)" in {
          val expected = FieldPredicate("intValue", "==", 1)
          dummyStore(expected).filter(_.intValue == 1)
        }
        "filter(_.longValue == 1)" in {
          val expected = FieldPredicate("longValue", "==", 1)
          dummyStore(expected).filter(_.longValue == 1)
        }
        "filter(_.stringValue == \"example\")" in {
          val expected = FieldPredicate("stringValue", "==", "example")
          dummyStore(expected).filter(_.stringValue == "example")
        }
        "filter(_.timestampValue == Timestamp.from(instant))" in {
          val instant = Instant.now()
          val expected = FieldPredicate("timestampValue", "==", Timestamp.from(instant))
          dummyStore(expected).filter(_.timestampValue == Timestamp.from(instant))
        }
        "filter(_.optIntValue == None)" in {
          val expected = FieldPredicate("optIntValue", "==", None)
          dummyStore(expected).filter(_.optIntValue == None)
        }
        "filter(_.optIntValue == Some(1))" in {
          val expected = FieldPredicate("optIntValue", "==", Some(1))
          dummyStore(expected).filter(_.optIntValue == Some(1))
        }
        "filter(_.optLongValue == None)" in {
          val expected = FieldPredicate("optLongValue", "==", None)
          dummyStore(expected).filter(_.optLongValue == None)
        }
        "filter(_.optLongValue == Some(1L))" in {
          val expected = FieldPredicate("optLongValue", "==", Some(1))
          dummyStore(expected).filter(_.optLongValue == Some(1L))
        }
        "filter(_.optStringValue == None)" in {
          val expected = FieldPredicate("optStringValue", "==", None)
          dummyStore(expected).filter(_.optStringValue == None)
        }
        "filter(_.optLongValue == Some(\"example\"))" in {
          val expected = FieldPredicate("optStringValue", "==", Some("example"))
          dummyStore(expected).filter(_.optStringValue == Some("example"))
        }
        "filter(_.optTimestampValue == None)" in {
          val expected = FieldPredicate("optTimestampValue", "==", None)
          dummyStore(expected).filter(_.optTimestampValue == None)
        }
        "filter(_.optTimestampValue == Some(Timestamp.from(instant)))" in {
          val instant = Instant.now()
          val expected = FieldPredicate("optTimestampValue", "==", Some(Timestamp.from(instant)))
          dummyStore(expected).filter(_.optTimestampValue == Some(Timestamp.from(instant)))
        }
      }
    }
  }
}
