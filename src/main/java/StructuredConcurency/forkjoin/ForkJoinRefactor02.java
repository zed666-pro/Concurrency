package StructuredConcurency.forkjoin;


import jdk.incubator.concurrent.StructuredTaskScope;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/5/29 10:22
 * @ClassName: ForkJoinRefactor01
 * @Description: TODO
 * @Version 1.0
 */
public class ForkJoinRefactor02 {
    private static final int THRESHOLD = 1_000;

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        long start = 0;
        long end = 1_000_000_000;
        long result = sum(start,end);
        long t2 = System.currentTimeMillis();
        System.out.println("result is:" + result);
        System.out.println("用时为：" + (t2 - t1));

    }


    public static long sum(long start,long end) throws Exception {

        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            Future<Long> result = scope.fork(() -> {
                if ((end - start) <= THRESHOLD) {
                    return sumSequentially(start, end);
                    } else {
                        Long mid = (start + end) / 2;
                        long leftValue = sum(start,mid);
                        long rightValue = sum(mid + 1,end);
                        return leftValue + rightValue;
                    }

            });
            scope.join();
            scope.throwIfFailed();
            return result.resultNow();
        }

    }



    private static long sumSequentially(long start,long end) {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}
