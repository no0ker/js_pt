package com.example;

import javax.script.ScriptException;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StarterPT {
    public static void main(String[] args) throws ScriptException, IOException, ExecutionException, InterruptedException {
        NashornPT nashornPT = new NashornPT();
        nashornPT.init();
        timer(() -> nashornPT.processRequest());

        GraalJsPT graalJsPT = new GraalJsPT();
        graalJsPT.init();
        timer(() -> graalJsPT.processRequest());
    }

    private static void timer(Callable<?> callable) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        List<Future<?>> futures = new ArrayList<>();

        ZonedDateTime start = ZonedDateTime.now();
        for (int i = 0; i < 300; i++) {
            futures.add(executorService.submit(callable));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        ZonedDateTime end = ZonedDateTime.now();

        System.out.println("time = " + Duration.between(start, end).toMillis() + " ms");
    }
}
