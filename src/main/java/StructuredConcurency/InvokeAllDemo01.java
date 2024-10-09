package StructuredConcurency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/5/15 16:16
 * @ClassName: InvokeAllDemo01
 * @Description: TODO
 * @Version 1.0
 */
public class InvokeAllDemo01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = pool.submit(() -> {
                Thread.sleep(70000);
                System.out.println("task1");
                return "task1";
            });
            futures.add(future);
        }
        for (int i = 0; i < futures.size(); i++) {
            System.out.println(futures.get(i).get());
        }
    }


}
