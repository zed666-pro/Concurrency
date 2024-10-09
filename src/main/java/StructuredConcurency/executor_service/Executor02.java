package StructuredConcurency.executor_service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: shengaojie
 * @create: 2024-10-09
 **/

public class Executor02 {
    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            for (int i = 0; i < 4; i++) {
                executor.submit(
                        () -> {
                            throw new RuntimeException("throw.....");
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000);
        System.out.println("主线程继续执行");
    }
}
