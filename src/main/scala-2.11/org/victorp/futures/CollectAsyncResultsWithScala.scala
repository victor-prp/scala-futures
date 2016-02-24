package org.victorp.futures


import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Await, Future}
import org.victorp.futures.WorkUtil._


/**
 * @author victorp
 */
object CollectAsyncResultsWithScala extends App {
  implicit val ec = ExecutionContext.global

  val f = doItInTheFuture()
  println(s"future tasks are started, we can now wait for its results")
  val result:(String,Int) = Await.result(f,100 millis)
  println(s"future tasks are finished, results: $result")


  def doItInTheFuture() :Future[(String,Int)]= {


    val work1F = Future {
      doWork(10, result = "work1")
    }

    val work2F = Future {
      doWork(10, result = 2)
    }


    val twoWorksF =
      for {
        work1 <- work1F
        work2 <- work2F
      } yield (work1, work2)


    twoWorksF
  }

}
