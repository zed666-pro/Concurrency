package Thread.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//任务的取消
public class ThreadPool06 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Worker2 worker2 = new Worker2();
        Future<String> submit = executorService.submit(worker2);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        submit.cancel(true);
        System.out.println("任务的终止情况："+submit.isCancelled());
        executorService.shutdown();
    }
}

class Worker2 implements Callable<String> {
    @Override
    public String call() throws Exception {
       while (true){
           System.out.println("工人正在工作");
           Thread.sleep(1000);
       }
    }
}
