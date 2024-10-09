package StructuredConcurency.ScopedValue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/14 18:16
 * @ClassName: UseThreadLocal01
 * @Description: TODO
 * @Version 1.0
 */
public class UseThreadLocal01 {

    static ThreadLocal<String> local = new ThreadLocal<>();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        Future<String> future1 = pool.submit(() -> {
            local.set(Thread.currentThread().getName());
            System.out.println(local.get());
            local.remove();
            return "future1";
        });


        Future<String> future2 = pool.submit(() -> {
            local.set(Thread.currentThread().getName());
            System.out.println(local.get());
            local.remove();
            return "future2";
        });

        System.out.println(future1.get());
        System.out.println(future2.get());
        pool.shutdown();

    }

}
