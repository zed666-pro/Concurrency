package Thread.ThreadApi.interrupt;

import java.util.concurrent.TimeUnit;

/*
* 如果一个线程在没有执行可中断方法之前就被打断。那么接下来在执行可中断的方法，比如sleep会怎么样
* */
public class interrupt04 {
    public static void main(String[] args) {
        //判断当前先是否被中断
        System.out.println("Main thread is interrupted ?"+ Thread.interrupted());
        //中断当前线程
        Thread.currentThread().interrupt();
        System.out.println("Main thread is interrupted?"+ Thread.currentThread().isInterrupted());


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("i will be interrupted still");
        }

    }
}


