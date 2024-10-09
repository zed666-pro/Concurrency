package StructuredConcurency.my_structured_concurrency;


import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author shengaojie @Date 2023/9/4 09:33 @ClassName: TestMyScope01 @Description: TODO @Version 1.0
 */
public class TestMyScope01 {

    @Test
    public void test01() {
        long start = System.currentTimeMillis();
        try (var scope = new MyScope<String>()) {

            Future<String> fork1 =
                    scope.fork(
                            () -> {
                                Thread.sleep(3000);
                                System.out.println("task1");
                                return "StructuredTaskScope to do task1";
                            });

            Future<String> fork2 =
                    scope.fork(
                            () -> {
                                Thread.sleep(2000);
                                System.out.println("task2");
                                return "StructuredTaskScope to do task2";
                            });

            Future<String> fork3 =
                    scope.fork(
                            () -> {
                                Thread.sleep(1000);
                                System.out.println("task3");
                                return "StructuredTaskScope to do task3";
                            });

            Future<String> fork4 =
                    scope.fork(
                            () -> {
                                Thread.sleep(500);
                                System.out.println("task4");
                                return "StructuredTaskScope to do task4";
                            });

            scope.join();
            Queue<Future<String>> results = scope.results();
            System.out.println("results.size is" + results.size());
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.poll().get());
            }
            long end = System.currentTimeMillis();
            System.out.println("用时为：" + (end - start));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
