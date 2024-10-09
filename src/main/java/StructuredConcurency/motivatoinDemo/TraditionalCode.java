package StructuredConcurency.motivatoinDemo;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: shengaojie
 * @create: 2024-04-23
 **/

public class TraditionalCode {


    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<String> user = pool.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "user";
        });

        Future<String> order = pool.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
                int i = 1 / 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("fetch order");
            return "order";
        });

        String res = user.get();
        String res1 = order.get();

        System.out.println(res + res1);

    }

}
