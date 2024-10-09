package asynchronous.completableFuture.thenRun;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:56
 * @ClassName: thenRun
 * @Description: TODO
 * @Version 1.0
 */
public class thenRunDemo {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        ExecutorService pool = Executors.newFixedThreadPool(4);
        CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取filter_word文件的内容");
            String filterWords = CommonUtils.readFile("demo/filter_words.txt");
            return filterWords;
        },pool).thenApplyAsync((content) -> {
            CommonUtils.printThreadLog("转换敏感词汇为数组");
            String[] wordsArray = content.split(",");
            return wordsArray;
        },pool).thenRunAsync(() -> {
            CommonUtils.printThreadLog("任务完成了");
        },pool);

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");


    }

}
