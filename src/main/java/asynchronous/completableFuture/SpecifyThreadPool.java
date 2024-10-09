package asynchronous.completableFuture;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:50
 * @ClassName: SpecifyThreadPool
 * @Description: 可以指定特定的线程池去创建异步任务，而不是使用默认的ForkJoinPool
 * @Version 1.0
 */
public class SpecifyThreadPool {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("开始异步读取文件");
            String content = CommonUtils.readFile("demo/news.txt");
            CommonUtils.printThreadLog("异步读取文件完成");
            return content;
        },pool);

    }
}
