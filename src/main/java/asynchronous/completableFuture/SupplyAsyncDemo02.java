package asynchronous.completableFuture;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:39
 * @ClassName: SupplyAsyncDemo02
 * @Description: TODO
 * @Version 1.0
 */
public class SupplyAsyncDemo02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(() -> {
                String news = CommonUtils.readFile("demo/news.txt");
                return news;
        });

        CommonUtils.printThreadLog("here are not blocked,main continue");

        String news = newsFuture.get();
        CommonUtils.printThreadLog("news =" + news);
        CommonUtils.printThreadLog("main end");
    }
}
