package org.victorp.futures

import java.util.concurrent._
import java.util.function.Supplier


/**
 * @author victorp
 */
object CollectAsyncResultsWithJava8 extends App  {

  val threadPool:ExecutorService = Executors.newFixedThreadPool(10)

  val f = doItInTheFuture()
  println(s"future tasks are started, we can now wait for its results")
  val result:(Int,String) = f.get(100,TimeUnit.MILLISECONDS).get(100,TimeUnit.MILLISECONDS)
  println(s"future tasks are finished, results: $result")

  def doItInTheFuture() :CompletableFuture[CompletableFuture[(Int,String)]]= {


    val work1: CompletableFuture[Int] = CompletableFuture.supplyAsync(toSupplier {
      WorkUtil.doWork(10, result = 2)
    }, threadPool)

    val work2: CompletableFuture[String] = CompletableFuture.supplyAsync(toSupplier {
      WorkUtil.doWork(10, result = "work1")
    }, threadPool)



    val twoWorksF: CompletableFuture[CompletableFuture[(Int,String)]] = work1.thenApply(toFunction(r1 => {
      work2.thenApply(toFunction(r2 => {
        (r1, r2)
      }))
    }
    ))

    twoWorksF
  }


  def toFunction[Input,R](fun: Input => R): java.util.function.Function[Input,R] = {
    new java.util.function.Function[Input,R]{
      override def apply(input: Input): R = fun(input)
    }
  }

  def toCallable[R](fun: =>R):Callable[R] = {
    new Callable[R] {
      override def call(): R = fun
    }
  }

  def toSupplier[R](fun: =>R):java.util.function.Supplier[R] = {
    new Supplier[R] {
      override def get(): R = fun
    }
  }

}
