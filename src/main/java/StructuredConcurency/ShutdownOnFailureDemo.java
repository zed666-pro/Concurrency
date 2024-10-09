package StructuredConcurency;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @Author shengaojie
 * @Date 2023/4/12 14:08
 * @ClassName: ShutdownOnFailureDemo
 * @Description:
 */
public class ShutdownOnFailureDemo {


    public static void handle() {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            ArrayList<Callable<String>> list = new ArrayList<>();
            List<Future<String>> result = new ArrayList<>();
            Future<String> user = scope.fork(() -> {

                TimeUnit.SECONDS.sleep(20);
                System.out.println("thread1 working....");


                return "findUser";
            });

            Future<String> order = scope.fork(() -> {
                while (true) {
                    //TimeUnit.MILLISECONDS.sleep(10);
                    System.out.println("thread2 working....");
                }
            });

            scope.join();
            scope.throwIfFailed();
            System.out.println("target result : " + (user.resultNow() + order.resultNow()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
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
        
        System.out.println("main thread .......");
    }
}
