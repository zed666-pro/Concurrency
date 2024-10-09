package StructuredConcurency.forkjoin.task;

import java.util.concurrent.RecursiveTask;

/**
 * @Author shengaojie
 * @Date 2023/5/29 10:05
 * @ClassName: CountTask
 * @Description: TODO
 * @Version 1.0
 */
public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private long start;
    private long end;
    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            return sumSequentially();
        } else {
            long mid = (start + end) / 2;
            SumTask leftTask = new SumTask(start, mid);
            SumTask rightTask = new SumTask(mid + 1, end);

            leftTask.fork();
            rightTask.fork();

            long leftResult = leftTask.join();
            long rightResult = rightTask.join();

            return leftResult + rightResult;
        }
    }

    private long sumSequentially() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
    }








