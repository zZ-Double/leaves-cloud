package com.leaves.common.base;

import com.leaves.common.result.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * dubbo 异步解构
 */
@Slf4j
public class BaseProvider {

    public static final ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * 3);


    protected CompletableFuture buildFuture(Result result){
        return CompletableFuture.supplyAsync(() -> result, executor);
    }

    public static CompletableFuture buildFuture(Supplier<Result> supplier) {
        return CompletableFuture.supplyAsync(() -> supplier.get(), executor);
    }
}
