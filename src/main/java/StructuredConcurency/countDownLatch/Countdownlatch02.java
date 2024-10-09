package StructuredConcurency.countDownLatch;

import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * @Author shengaojie @Date 2023/9/4 16:15 @ClassName: Countdownlatch02 @Description: TODO @Version
 * 1.0
 */
public class Countdownlatch02 {

    @Test
    public void test() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        String lock = "locker";

        Thread thread =
                new Thread(
                        () -> {
                            countDownLatch.countDown();
                            System.out.println(lock);
                        });
        thread.start();
        System.out.println("主线程等待。。。。。");
        countDownLatch.await();
        // let the tcp request be sent out
        TimeUnit.MILLISECONDS.sleep(5);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(45);
    }

    @Test
    public void test1() {
        String lock = "locker";
        try (var scope = new StructuredTaskScope<>()) {
            scope.fork(
                    () -> {
                        System.out.println(lock);
                        return null;
                    });
        }
    }
}
