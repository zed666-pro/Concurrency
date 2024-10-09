package thread_.method;


import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @description: join方法
 * @author: shengaojie
 * @create: 2023-11-15
 **/

public class Join {
    @Test
    public void test01() {
        // join
        // thread.join:会让当前线程进入阻塞，而不是thread线程
        // 如果没有join方法的话，t1，t2，main线程是交替输出的
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "#" + i);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {

                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "#" + i);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "#" + i);
        }


    }
}
