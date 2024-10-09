package asynchronous.completableFuture.thenApply;

import asynchronous.kit.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author shengaojie
 * @Date 2023/4/11 17:27
 * @ClassName: thenApplyDemo02
 * @Description: TODO
 * @Version 1.0
 */
public class thenApplyDemo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String[]> wordsFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取filter_words文件");
            String filterWords = CommonUtils.readFile("demo/filter_words.txt");
            return filterWords;
        }).thenApplyAsync((content) -> {
            CommonUtils.printThreadLog("将敏感词汇转成数组");
            String[] wordsArray = content.split(",");
            return wordsArray;
        });


        CommonUtils.printThreadLog("main continue");
        String[] wordsArray = wordsFuture.get();
        CommonUtils.printThreadLog("filterWords ="+ Arrays.toString(wordsArray));

    }
}
