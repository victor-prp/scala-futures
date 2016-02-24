package org.victorp.futures


import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import org.victorp.futures.ScalaWorkUtil._


/**
 * @author victorp
 */
object CollectAsyncResultsWithScala extends App {


  val f = doItInTheFuture()
  println(s"future tasks are started, we can now wait for its results")
  val result:(String,Int) = Await.result(f,1000 millis)
  println(s"future tasks are finished, results: $result")
  System.exit(0)



  def doItInTheFuture() :Future[(String,Int)]= {
    val work1F = doWorkInTheFuture(100, result = "work1")
    val work2F = doWorkInTheFuture(100, result = 2)

    val twoWorksF =
      for {
        work1 <- work1F
        work2 <- work2F
      } yield (work1, work2)


    twoWorksF
  }

}
