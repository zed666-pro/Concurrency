package asynchronous.completableFuture.thenApply;

import asynchronous.kit.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:56
 * @ClassName: thenApplyDemo
 * @Description: TODO
 * @Version 1.0
 */
public class thenApplyDemo01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String> filterFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取filter_words文件");
            String filterWords = CommonUtils.readFile("demo/filter_words.txt");
            return filterWords;
        });


        CompletableFuture<String[]> wordsFuture = filterFuture.thenApplyAsync((content) -> {
            CommonUtils.printThreadLog("将敏感词汇转成数组");
            String[] wordsArray = content.split(",");
            return wordsArray;
        });

        CommonUtils.printThreadLog("main continue");
        String[] wordsArray = wordsFuture.get();
        CommonUtils.printThreadLog("filterWords ="+ Arrays.toString(wordsArray));

    }
}
