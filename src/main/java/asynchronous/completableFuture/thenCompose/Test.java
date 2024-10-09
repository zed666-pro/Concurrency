package asynchronous.completableFuture.thenCompose;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author shengaojie
 * @Date 2023/4/11 21:15
 * @ClassName: Test
 * @Description: TODO
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(5);
        Future<String> future = pool.submit(() -> {
            System.out.println("使用Future创建异步任务");
            return "future";
        });

        ExecutorService pool1 = Executors.newFixedThreadPool(5);

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("使用CompletableFuture来创建异步任务");
            return "completableFuture";
        },pool1);


        Set<Callable<String>> callables = new HashSet<>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(1);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(2);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(3);
                return "Task 3";
            }
        });
        String result = pool.invokeAny(callables);
        System.out.println("result = " + result);
        pool.shutdown();
    }
}
