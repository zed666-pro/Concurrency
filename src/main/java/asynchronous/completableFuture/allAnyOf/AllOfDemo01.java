package asynchronous.completableFuture.allAnyOf;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * @Author shengaojie
 * @Date 2023/4/15 10:50
 * @ClassName: AllOfDemo01
 * @Description:  测试allOf方法的对当前线程的阻塞性：不会阻塞当前线程
 * @Version 1.0
 */
public class AllOfDemo01 {
    public static void main(String[] args) {

        CommonUtils.printThreadLog("main start");
        CompletableFuture<String> future1= CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("task1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("task2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2";
        });


        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("task3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task3";
        });

        CompletableFuture.allOf(future1,future2,future3).join();
        CommonUtils.printThreadLog("main continue without block.......");

        CommonUtils.sleepSecond(5);

    }
}
