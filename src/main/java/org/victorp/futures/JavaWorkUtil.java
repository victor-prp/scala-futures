package org.victorp.futures;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author victorp
 */
public class JavaWorkUtil {

    static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    static <R> CompletableFuture<R> doWorkInTheFuture(Long processingTimeMillis, boolean success, R result, RuntimeException exception ){
        return
            CompletableFuture.supplyAsync( () ->
                 doWork(processingTimeMillis, success,result,exception)
            , threadPool);

    }

    static private <R> R doWork(Long processingTimeMillis, boolean success, R result, RuntimeException exception ) {
        if (success) {
            System.out.println("Going to sleep " + processingTimeMillis + " millis");
            sleep(processingTimeMillis);
            System.out.println("The work is done");
            return result;
        }else {
            throw exception;
        }
    }

    static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> CompletableFuture<List<T>>  toSingleFuture(CompletableFuture<T>... futures){
        Stream<CompletableFuture<T>> futuresStream = Arrays.asList(futures).stream();
            return CompletableFuture.allOf(futures)
                    .thenApply(v -> futuresStream
                                    .map(CompletableFuture::join)
                                    .collect(toList())
                    );
    }


}
