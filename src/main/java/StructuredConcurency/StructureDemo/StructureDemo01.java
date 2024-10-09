package StructuredConcurency.StructureDemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/13 21:12
 * @ClassName: StructureDemo01
 * @Description: TODO
 * @Version 1.0
 */
public class StructureDemo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Future<String> future1 = pool.submit(() -> {
            int i = 1 / 0;
            System.out.println("print task1");
            return "task1";
        });

        Future<String> future2 = pool.submit(() -> {
            System.out.println("print task2");

            return "task2";
        });

        Future<String> future3 = pool.submit(() -> {
            System.out.println("print task3");
            return "future3";
        });

        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());

    }
}
