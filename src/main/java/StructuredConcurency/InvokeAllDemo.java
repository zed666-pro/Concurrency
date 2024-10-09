package StructuredConcurency;


import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author shengaojie
 * @Date 2023/4/12 13:59
 * @ClassName: InvokeAllDemo
 * @Description: TODO
 * @Version 1.0
 */
public class InvokeAllDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                futures.add(
                        scope.fork(() -> {
                                    TimeUnit.SECONDS.sleep(300);
                                    return "task complete";
                                }
                        )
                );
            }
            scope.join();
            for (Future<String> future : futures) {
                String s = future.resultNow();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}




