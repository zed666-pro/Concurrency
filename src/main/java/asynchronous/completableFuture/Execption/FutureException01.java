package asynchronous.completableFuture.Execption;

import java.util.concurrent.*;

/**
 * @Author shengaojie
 * @Date 2023/4/15 11:12
 * @ClassName: FutureException01
 * @Description: TODO
 * @Version 1.0
 */
public class FutureException01 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<String> future1 = pool.submit(() -> {
           TimeUnit.SECONDS.sleep(3);
            //int i = 1 / 0;
            throw new RuntimeException();

        });
          pool.shutdownNow();
        try {
            future1.get();
        } catch (InterruptedException e) {
            System.out.println("shutdown .....");

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main end");
    }
}
