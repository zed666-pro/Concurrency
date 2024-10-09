package asynchronous.completableFuture.thenCompose;

import asynchronous.kit.CommonUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author shengaojie
 * @Date 2023/4/11 20:40
 * @ClassName: thenComposeFuture
 * @Description: TODO
 * @Version 1.0
 */
public class thenComposeFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(1);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(2);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(3);
                return "Task 3";
            }
        });


        String result = executorService.invokeAny(callables);
        System.out.println("result = " + result);
        executorService.shutdown();
    }

    public static CompletableFuture<String> readFileFuture(String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("开始读取filter_words.txt中的敏感数据");
            String filterWords = CommonUtils.readFile(fileName);
            return filterWords;
        });
    }


    public static CompletableFuture<String[]> spiltFuture(String context) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("敏感数据转换成数组");
            String[] wordsArray = context.split(",");
            return wordsArray;
        });
    }
}
