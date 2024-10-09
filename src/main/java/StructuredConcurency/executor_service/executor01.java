package StructuredConcurency.executor_service;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author shengaojie @Date 2023/9/7 14:35 @ClassName: executor01 @Description: TODO @Version 1.0
 */
public class executor01 {

    ExecutorService executor = Executors.newFixedThreadPool(4);

    @Test
    public void test() throws InterruptedException {
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
