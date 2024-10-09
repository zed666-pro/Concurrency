package StructuredConcurency.ScopedValue;

import jdk.incubator.concurrent.ScopedValue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/14 18:15
 * @ClassName: UseScopedLocal01
 * @Description: TODO
 * @Version 1.0
 */
public class UseScopedLocal01 {

    public static final ScopedValue value = ScopedValue.newInstance();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<String> future1 = pool.submit(() -> {
            String call = ScopedValue.where(value, Thread.currentThread().getName()).call(() -> {
                System.out.println(value.get());
                return "future1";
            });
            return call;
        });

        Future<String> future2 = pool.submit(() -> {
            String call = ScopedValue.where(value, Thread.currentThread().getName()).call(() -> {
                System.out.println(value.get());
                return "future2";
            });

            return call;
        });
        System.out.println(future1.get());
        System.out.println(future2.get());
        pool.shutdown();
    }
}
