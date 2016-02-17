package org.victorp.futures


import scala.concurrent.{ExecutionContext, Await, Future}
import org.victorp.futures.WorkUtil._


/**
 * @author victorp
 */
object CollectAsyncResults extends App {
  implicit val ec = ExecutionContext.global

  doIt()

  def doIt() :Future[(String,Int)]= {


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

    println(s"twoWorksFuture is defined")
    twoWorksF
  }

}
