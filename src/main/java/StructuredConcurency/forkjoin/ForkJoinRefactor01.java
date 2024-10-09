package StructuredConcurency.forkjoin;

import jdk.incubator.concurrent.ScopedValue;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/5/29 10:22
 * @ClassName: ForkJoinRefactor01
 * @Description: TODO
 * @Version 1.0
 */
public class ForkJoinRefactor01 {

    public static final ScopedValue<Long> MAXSCOPE = ScopedValue.newInstance();
    public static final ScopedValue<Long> MINSCOPE = ScopedValue.newInstance();

    private static final int THRESHOLD = 1000;
    private static long sum = 0;

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        long start = 0;
        long end = 100000000;
        long result = sum(start, end);
        long t2 = System.currentTimeMillis();
        System.out.println("result is:" + result);
        System.out.println("用时为：" + (t2 - t1));
    }


    public static long sum(long start, long end) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<Long> result = scope.fork(() -> {
                System.out.println("thread name is:" + Thread.currentThread());
                Long totalNum = ScopedValue.where(MAXSCOPE, end).where(MINSCOPE, start).call(() -> {
                    if ((MAXSCOPE.get() - MINSCOPE.get()) < THRESHOLD) {
                        //                System.out.println(Thread.currentThread());
                        return sumSequentially(start, end);
                    } else {
                        Long mid = (start + end) / 2;
                        Future<Long> left = scope.fork(() -> {
                            return sum(start, mid);
                        });
                        Future<Long> right = scope.fork(() -> {
                            return sum(mid + 1, end);
                        });
                        Long leftValue = left.get();
                        Long rightValue = right.get();
                        return leftValue + rightValue;
                    }

                });

                return totalNum;
            });
            scope.join();
            scope.throwIfFailed();
            return result.resultNow();
        }

    }


    private static long sumSequentially(long start, long end) {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}
