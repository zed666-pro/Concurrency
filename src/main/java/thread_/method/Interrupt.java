package thread_.method;


import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @description: null
 * @author: shengaojie
 * @create: 2023-11-15
 **/

public class Interrupt {

    @Test
    public void test05() throws InterruptedException {
        // 如果当前线程进入阻塞状态，就可以调用该线程的interrupt方法，打断阻塞
        // 线程在阻塞状态下被打断，会抛出InterruptedException异常
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();


    }


    @Test
    public void test06() throws InterruptedException {
        // isInterrupted 判断线程是否被中断
        // 三个打印信息都是false；原因：
        // interrupt方法在打断可中断方法的执行时，如sleep，wait等，在捕获到异常
        // 信号之后，会擦除interupt的标识
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("I am be interrupted ?  %s" + isInterrupted());
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread is interrupted ?" + thread.isInterrupted());
        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread is interrupted ?" + thread.isInterrupted());


    }

    @Test
    public void test07() throws InterruptedException {
        // 来看打断的是非可中断方法
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });

        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread is interrupted ?" + thread.isInterrupted());
        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread is interrupted ?" + thread.isInterrupted());

    }


    @Test
    public void test08() throws InterruptedException {
        // interrupted 是一个静态方法，也用于判断当前线程是否被中断。
        // 和成员方法isInterrupted的区别：
        //      调用该方法会直接擦除掉线程的interrupt标识
        //      如果当前的线程被打断了，第一次调用interrupted方法会返回true，并且将interrupt
        //      标识擦除，以后再次调用该方法会false
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("thread is interrupted ?" + Thread.interrupted());

            }
        });

        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();

    }

    @Test
    public void test09() {
        // 如果一个线程执行可中断方法之前就已经被中断了
        // 在执行到可中断方法的时候会直接捕获异常
        System.out.println("Main thread is interruptd ? " + Thread.interrupted());
        Thread.currentThread().interrupt();

        System.out.println("Main thread is interruptd ? " + Thread.currentThread().isInterrupted());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("I will be interrupted still");
        }

    }
}
