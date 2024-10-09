package asynchronous.completableFuture.thenApply;

import asynchronous.kit.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/11 17:11
 * @ClassName: thenApplyFuture
 * @Description: thenApply对应的Future模式
 * @Version 1.0
 */
public class thenApplyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<String> filterWordsFuture = pool.submit(() -> {
            CommonUtils.printThreadLog("读取filter_words文件");
            String filterWords = CommonUtils.readFile("demo/filter_words.txt");
            return filterWords;
        });

        Future<String[]> wordsArrayfuture = pool.submit(() -> {
            CommonUtils.printThreadLog("文件内容转换成敏感数组");
            String filterWords = filterWordsFuture.get();
            String[] wordsArray = filterWords.split(",");

            return wordsArray;

        });

        CommonUtils.printThreadLog("main continue");
        String [] wordArray = wordsArrayfuture.get();
        CommonUtils.printThreadLog("filterWords =" + Arrays.toString(wordArray));
        CommonUtils.printThreadLog("main end");
    }
}
