package org.victorp.futures;

import javafx.util.Pair;

import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * @author victorp
 */
public class CollectAsyncResultsWithJava8V2 {


    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Future<Pair<Integer,String>> f = doItInTheFuture();
        out.println("future tasks are started, we can now wait for its results");
        Pair<Integer,String> result = f.get(1000, TimeUnit.MILLISECONDS);
        out.println("future tasks are finished, results: " + result.toString());
        System.exit(0);
    }

    private static Future<Pair<Integer, String>> doItInTheFuture() {
        CompletableFuture<Integer> work1 = JavaWorkUtil.doWorkInTheFuture(100L, true, 1, null);
        CompletableFuture<String> work2 = JavaWorkUtil.doWorkInTheFuture(100L, true, "work2", null);


        Future<Pair<Integer,String>> result =
                CompletableFuture.allOf(work1,work2)
                        .thenApply( v -> new Pair<>(work1.join(), work2.join()));


        return result;
    }

}
