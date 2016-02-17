package org.victorp.futures
import org.scalatest.FunSuite

/**
 * @author victorp
 */
class WorkUtilTest extends FunSuite {

  test("process with success") {
    assert(WorkUtil.doWork(10) == null)
  }

  test("process with failure") {
    intercept[RuntimeException] {
      WorkUtil.doWork(processingTimeMillis = 10,success = false)
    }
  }

  test("process with specific result") {
    val withResult = "test"
    assert(WorkUtil.doWork(10,result = withResult) == withResult)
  }


  test("process with specific failure") {
    intercept[IllegalAccessError] {
      WorkUtil.doWork(processingTimeMillis = 10,
                      success = false,
                      exception = new IllegalAccessError )
    }
  }
}
