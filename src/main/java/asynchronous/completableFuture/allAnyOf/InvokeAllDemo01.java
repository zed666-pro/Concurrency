package asynchronous.completableFuture.allAnyOf;

import asynchronous.kit.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author shengaojie
 * @Date 2023/4/15 10:54
 * @ClassName: InvokeAllDemo01
 * @Description: 测试后InvokeAll方法的阻塞性：会阻塞当前线程
 * @Version 1.0
 */
public class InvokeAllDemo01 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        CommonUtils.printThreadLog("main start");
        List<Callable<String>> list = new ArrayList<>();
        list.add(() -> {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("task1");
            return "task1";
        });

        list.add(() -> {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("task2");
            return "task2";
        });


        list.add(() -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("task3");
            return "task3";
        });
        List<Future<String>> futures = pool.invokeAll(list);
        CommonUtils.printThreadLog("main continue without block");

    }
}
