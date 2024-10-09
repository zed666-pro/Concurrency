package asynchronous.completableFuture;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:34
 * @ClassName: RunAsyncDemo02
 * @Description: TODO
 * @Version 1.0
 */
public class RunAsyncDemo02 {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        CompletableFuture.runAsync(() -> {
          String content = CommonUtils.readFile("demo/news.txt");
          CommonUtils.printThreadLog(content);
        });

        CommonUtils.printThreadLog("here are not blocked,main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
