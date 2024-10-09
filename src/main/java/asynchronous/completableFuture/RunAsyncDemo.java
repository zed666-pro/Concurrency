package asynchronous.completableFuture;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:25
 * @ClassName: RunAsyncDemo
 * @Description: TODO
 * @Version 1.0
 */
public class RunAsyncDemo {

    public static void main(String[] args) {

        CommonUtils.printThreadLog("main start");
        CompletableFuture.runAsync(() -> {
              CommonUtils.printThreadLog("开始读取文件");
              //休眠来模拟一个长时间的任务（如：读取文件，网络请求等）
              CommonUtils.sleepSecond(3);
              CommonUtils.printThreadLog("读取文件结束");
        });

        CommonUtils.printThreadLog("here are not blocked,main continue");
        //等待异步任务执行完成
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
