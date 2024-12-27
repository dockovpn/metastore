package io.dockovpn.metastore.macros

import io.dockovpn.metastore.predicate.Predicates.{FieldPredicate, Predicate}
import io.dockovpn.metastore.store.AbstractStore
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import scala.concurrent.Future

class FilterMacrosSpec extends AnyWordSpec
  with ScalaFutures
  with BeforeAndAfter
  with MockFactory {
  
  final case class DummyObject(id: String, value: Int)
  
  trait ApplyPredicateFilter {
    def filter(predicate: Predicate): Future[Seq[DummyObject]]
  }
  
  class DummyStore(applyFilter: ApplyPredicateFilter) extends AbstractStore[DummyObject] {
    
    override def contains(k: String): Future[Boolean] = ???
    
    override def filter(predicate: Predicate): Future[Seq[DummyObject]] = applyFilter.filter(predicate)
    
    override def get(k: String): Future[Option[DummyObject]] = ???
    
    override def put(k: String, v: DummyObject): Future[Unit] = ???
    
    override def update(k: String, v: DummyObject): Future[Unit] = ???
    
    override def getAll(): Future[Seq[DummyObject]] = ???
  }
  
  private def dummyStore(predicate: Predicate): AbstractStore[DummyObject] = {
    val applyPredicateFilter = mock[ApplyPredicateFilter]
    (applyPredicateFilter.filter _).expects(predicate).returns(Future.successful(Seq.empty[DummyObject]))
    
    new DummyStore(applyPredicateFilter)
  }
  
  "FilterMacros" should {
    "generate correct Predicate" when {
      "filter(_.value == 1)" in {
        val expected = FieldPredicate("value", "==", 1)
        dummyStore(expected).filter(_.value == 1)
      }
    }
  }
}
