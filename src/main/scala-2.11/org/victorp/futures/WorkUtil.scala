package org.victorp.futures

/**
 * @author victorp
 */
object WorkUtil {

  val defaultException = new RuntimeException("Operation failed")

  def doWork[Result](processingTimeMillis: Long,
                     success: Boolean = true,
                     result: Result = null,
                     exception: Throwable = defaultException):Result  = {

    println(s"Going to sleep $processingTimeMillis millis")
    Thread.sleep(processingTimeMillis)
    success match {
      case true => result
      case false => throw exception
    }
  }
}
