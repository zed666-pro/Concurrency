package Thread.PrincipleOfThreadPool;

import Thread.PrincipleOfThreadPool.core.BasicThreadPool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException, RunnableDenyException {

        //定义线程池初始化
        final BasicThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "is running is done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            for (; ; ) {
                System.out.println("getActiveCount:" + threadPool.getActiveCount());
                System.out.println("getQueueSize:" + threadPool.getQueueSize());
                System.out.println("------------------------");
                TimeUnit.SECONDS.sleep(2);
            }
        }

    }
}
