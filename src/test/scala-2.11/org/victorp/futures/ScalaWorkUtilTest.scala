package org.victorp.futures
import org.scalatest.FunSuite

/**
 * @author victorp
 */
class ScalaWorkUtilTest extends FunSuite {

  test("process with success") {
    assert(ScalaWorkUtil.doWorkInTheFuture(10) == null)
  }

  test("process with failure") {
    intercept[RuntimeException] {
      ScalaWorkUtil.doWorkInTheFuture(processingTimeMillis = 10,success = false)
    }
  }

  test("process with specific result") {
    val withResult = "test"
    assert(ScalaWorkUtil.doWorkInTheFuture(10,result = withResult) == withResult)
  }


  test("process with specific failure") {
    intercept[IllegalAccessError] {
      ScalaWorkUtil.doWorkInTheFuture(processingTimeMillis = 10,
                      success = false,
                      exception = new IllegalAccessError )
    }
  }
}
