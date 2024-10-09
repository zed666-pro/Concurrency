package StructuredConcurency;

import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author shengaojie
 * @Date 2023/4/14 10:50
 * @ClassName: CommonSituation01
 * @Description: TODO
 * @Version 1.0
 */
public class CommonSituation01 {

    static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);


        Future<String> future1 = pool.submit(() -> {
            System.out.println("hello world");
            int i = 0;
            return "task1";
        });
        Future<String> future2 = pool.submit(() -> {
            return "task2";
        });

        String str1 = future1.get();
        String str2 = future2.get();

        pool.toString();
        handleTest(handle(str1, str2));

    }

    @Test
    public void common() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> fork = scope.fork(() -> {
                System.out.println("hello world");
                int i = 0;
                return "task1";
            });

            Future<String> fork1 = scope.fork(() -> {
                return "task2";
            });

            scope.join();
            scope.throwIfFailed();
            String str1 = fork.resultNow();
            String str2 = fork1.resultNow();
            handleTest(handle(str1, str2));

        }
    }

    public static boolean handle(String str1, String str2) {
        System.out.println(str1 + str2);
        return false;
    }

    public static void handleTest(boolean flag) {

    }


    @Test
    public void test1() throws InterruptedException, IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        TimeUnit.SECONDS.sleep(30);
        AtomicInteger count = new AtomicInteger();
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> {
                    System.out.println("参数" + (count.getAndIncrement()));
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        System.out.println("耗时" + (System.currentTimeMillis() - start));
        System.in.read();
    }
}

class Test1 implements Callable {

    @Override
    public Object call() throws Exception {
        return null;
    }
}
