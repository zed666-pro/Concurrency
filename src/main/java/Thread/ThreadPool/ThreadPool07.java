package Thread.ThreadPool;

import java.util.concurrent.*;

public class ThreadPool07 {
    public static void main(String[] args) {
        int taskNum = 8;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<Long> service = new ExecutorCompletionService<>(executorService);

        for (int i = 0; i < taskNum; i++) {
            service.submit(new Worker03());
        }
        Future<Long> future = null;
        Long totalTime = 0L;

        for (int i = 0; i < taskNum; i++) {
            try {
                future = service.take();
                totalTime += future.get();

            } catch (InterruptedException  | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(taskNum+"个任务的执行总时间为:"+totalTime+"毫秒");
        executorService.shutdown();
    }
}

class Worker03 implements Callable<Long> {


    @Override
    public Long call() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
