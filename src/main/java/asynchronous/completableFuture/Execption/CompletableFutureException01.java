package asynchronous.completableFuture.Execption;

import java.util.concurrent.CompletableFuture;

/**
 * @Author shengaojie
 * @Date 2023/4/15 11:56
 * @ClassName: CompletableFutureException01
 * @Description: TODO
 * @Version 1.0
 */
public class CompletableFutureException01 {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int i = 1/0;
            return "task1";
        }).exceptionally(ex -> {
            if (ex instanceof InterruptedException){
                System.out.println("中断异常");
                throw new RuntimeException(ex);
            }

            if(ex instanceof RuntimeException){
                System.out.println("发生了运行时异常");
                throw new RuntimeException(ex);
            }
            return null;
        });

        String join = future.join();
        System.out.println(join);
    }
}
