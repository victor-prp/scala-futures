package org.victorp.futures

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import org.victorp.futures.ScalaWorkUtil._

/**
 * @author victorp
 */
object CollectAsyncResultsWithScalaV2_1  extends App {

  val f = doItInTheFuture()
  val recovered = f.recover {
    case e:RuntimeException => {
      println(s"Exception was thrown: $e")
      ("",0)
    }
  }


  println(s"future tasks are started, we can now wait for its results")
  val result:(String,Int) = Await.result(recovered,1000 millis)
  println(s"future tasks are finished, results: $result")
  System.exit(0)



  def doItInTheFuture() :Future[(String,Int)]= {
    val work1F = doWorkInTheFuture(100, result = "work1")
    val work2F = doWorkInTheFuture(100, success=false,result = 2)

    val twoWorksF:Future[Seq[Any]] = Future.sequence(List(work1F,work2F))
    twoWorksF.map(workResults => (work1F.value.get.get,work2F.value.get.get))
  }

}