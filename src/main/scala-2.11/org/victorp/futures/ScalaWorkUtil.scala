package org.victorp.futures

import scala.concurrent.{ExecutionContext, Future}

/**
 * @author victorp
 */
object ScalaWorkUtil {

  implicit val ec = ExecutionContext.global
  val defaultException = new RuntimeException("Operation failed")

  def doWorkInTheFuture[Result](processingTimeMillis: Long,
                     success: Boolean = true,
                     result: Result = null,
                     exception: Throwable = defaultException):Future[Result]  = Future{
    println(s"Going to sleep $processingTimeMillis millis")
    Thread.sleep(processingTimeMillis)
    println("The work is done")
    success match {
      case true => result
      case false => throw exception
    }
  }

  def  sequence[R](futures:Seq[Future[R]]):Future[Seq[R]] = {
    Future.sequence(futures)
  }
}
