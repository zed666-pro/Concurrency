package StructuredConcurency.countDownLatch;

import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @Author shengaojie
 * @Date 2023/5/31 14:57
 * @ClassName: CountDownLatch01
 * @Description: TODO
 * @Version 1.0
 */
public class CountDownLatch01 {


    @Test
    public void original() throws InterruptedException {
        ArrayList<Object> arrayList = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(arrayList.size());
        new Thread(() -> {
            System.out.println("task1");
            latch.countDown();
        }).start();


        new Thread(() -> {
            System.out.println("task2");
            latch.countDown();
        }).start();


        new Thread(() -> {
            try {
                Thread.sleep(3100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task3");
            latch.countDown();
        }).start();

       
    }

    public void update() throws InterruptedException, ExecutionException, TimeoutException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            scope.fork(() -> {
                System.out.println("task1");
                return null;
            });

            scope.fork(() -> {
                System.out.println("task2");
                return null;
            });

            scope.fork(() -> {
                System.out.println("task3");
                return null;
            });

            scope.joinUntil(Instant.now().plusSeconds(30));
            scope.throwIfFailed();
        }

    }

}
