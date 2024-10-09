package StructuredConcurency;

import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @Author shengaojie @Date 2023/5/26 14:06 @ClassName: SequentialDemo01 @Description: TODO @Version
 * 1.0
 */
public class SequentialDemo01 {

    @Test
    public void original() throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("start is :" + start);
        String result1 = method1();
        Object result2 = method2();
        System.out.println(result1 + result2);
        long end = System.currentTimeMillis();
        System.out.println("end is :" + end);
        System.out.println("end - start =" + (end - start));
    }

    @Test
    public void refactor() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Future<String> result1 = scope.fork(() -> method1());
            Future<Object> result2 = scope.fork(() -> method2());

            scope.join();
            scope.throwIfFailed();
            conbime(result1.resultNow(), result2.resultNow());
        }
        long end = System.currentTimeMillis();
        System.out.println("end - start =" + (end - start));
    }

    public String method1() throws InterruptedException {

        Thread.sleep(5000);
        return "hello";
    }

    public Object method2() throws InterruptedException {
        Thread.sleep(1000);
        int i = 1 / 0;
        return new Object();
    }

    public void conbime(String str, Object obj1) {

        System.out.println(str + obj1);
    }
}
