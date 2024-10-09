package StructuredConcurency;

import java.util.concurrent.*;

/**
 * @description:
 * @author: shengaojie
 * @create: 2024-03-22
 **/

public class InvokeAllDisAdvantage {
    private static ExecutorService exec = Executors.newCachedThreadPool();


    public static void handle() throws InterruptedException {
        Future<String> user = exec.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("thread1 working....");

            }
            return "findUser";
        });
        Future<String> order = exec.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("thread2 working....");

            }

            return "fetchOrder";
        });

        String res2 = null;
        String res1 = null;
        try {
            res1 = user.get();
            res2 = order.get();
        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task result : " + res1 + res2);
    }

    public static void main(String[] args) {
        // 创建一个新线程来运行handle方法
        Thread handleThread = new Thread(() -> {
            try {
                handle();
            } catch (Exception e) {
                // 如果handle方法抛出异常，输出异常信息
                e.printStackTrace();
            }
        });

        // 启动新线程
        handleThread.start();

        // 之后在主线程中中断handleThread
        try {
            // 主线程暂停5秒，让handleThread有些时间执行任务
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 中断handleThread，这会将handleThread的中断状态设置为true
        handleThread.interrupt();
        System.out.println("main thread stop...........");
    }


}
