package Thread.ThreadPool;

import java.util.concurrent.*;

public class ThreadPool01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

      ThreadPoolExecutor executor =  new ThreadPoolExecutor(2, 4,30, TimeUnit.SECONDS,
               new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

      executor.execute(()->{
          System.out.println("execute the runnable task");
        }
      );

        Future<String> submit = executor.submit(() ->
                "execute the callable task and return"
        );

        System.out.println(submit.get());

    }
}
