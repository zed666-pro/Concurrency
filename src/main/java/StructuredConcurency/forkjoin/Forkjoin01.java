package StructuredConcurency.forkjoin;

import StructuredConcurency.forkjoin.task.SumTask;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/5/29 10:04
 * @ClassName: forkjoin01
 * @Description: TODO
 * @Version 1.0
 */
public class Forkjoin01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask task = new SumTask(1, 1_000_000_000);

        Future<Long> result = forkJoinPool.submit(task);
        System.out.println("result is: " + result.get());
        long end = System.currentTimeMillis();
        System.out.println("forkjoin框架用时为：" + (end - start));
        forkJoinPool.shutdown();

    }


    @Test
    public void count() {
        long start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i <= 1_000_000_000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("串行执行用时：" + (end - start));
        System.out.println("sum is:" + sum);
    }
}
