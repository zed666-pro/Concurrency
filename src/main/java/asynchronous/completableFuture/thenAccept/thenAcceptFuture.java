package asynchronous.completableFuture.thenAccept;

import asynchronous.kit.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @Author shengaojie
 * @Date 2023/4/11 19:25
 * @ClassName: thenAcceptFuture
 * @Description: TODO
 * @Version 1.0
 */
public class thenAcceptFuture {

    public static void main(String[] args) throws InterruptedException,ExecutionException {
        CommonUtils.printThreadLog("main start");

        ExecutorService pool = Executors.newFixedThreadPool(4);
        //1.读取filter_words文件中的敏感词汇
        Future<String> filterwordsFuture = pool.submit(() -> {
            CommonUtils.printThreadLog("读取filter_words文件");
            String filterWords = CommonUtils.readFile("demo/filter_words.txt");
            return filterWords;
        });
        //2.将敏感词汇转成数组
        Future<String[]> wordsArrayFuture = pool.submit(() -> {
            CommonUtils.printThreadLog("将敏感词汇转成数组");
            String filterwords = filterwordsFuture.get();
            String[] wordsArray = filterwords.split(",");
            return wordsArray;
        });
        //3.使用上一个future的数据，Runnable接口的run方法中没有抛出异常
        // pool.submit()中传入一个Runnalbe接口，在run方法中使用get获取上一个future的数据
        pool.submit(() -> {
            try {
             String [] wordsArray = wordsArrayFuture.get();
             CommonUtils.printThreadLog("filterwords = " + Arrays.toString(wordsArray));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
       String [] wordsArray = wordsArrayFuture.get();
       CommonUtils.printThreadLog("filterwords = " + Arrays.toString(wordsArray));


        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");


    }
}
