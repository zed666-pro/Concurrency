package Thread.CountDownLatch;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @Author shengaojie
 * @Date 2023/5/11 10:57
 * @ClassName: Demo1
 * @Description: TODO
 * @Version 1.0
 */
public class Demo1 {


    @Test
    public void test1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task1 执行......");
            latch.countDown();
        }).start();


        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task2 执行......");
            latch.countDown();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task3 执行......");
            latch.countDown();
        }).start();

        latch.await();
        System.out.println("主线程等待三个任务执行完成开始执行");

    }
}
