package asynchronous.completableFuture.thenCompose;

import asynchronous.kit.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author shengaojie @Date 2023/4/11 19:54 @ClassName: thenComposeDemo01 @Description:
 * TODO @Version 1.0
 */
public class thenComposeDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CommonUtils.printThreadLog("main start");
    String fileName = "demo/filter_words.txt";
    CompletableFuture<String[]> future =
        readFileFuture(fileName)
            .thenCompose(
                (context) -> {
                  return spiltFuture(context);
                });

    CommonUtils.printThreadLog("main continue");
    String[] wordsArray = future.get();
    CommonUtils.printThreadLog("filterwords =" + Arrays.toString(wordsArray));
  }

  public static CompletableFuture<String> readFileFuture(String fileName) {
    return CompletableFuture.supplyAsync(
        () -> {
          CommonUtils.printThreadLog("开始读取filter_words.txt中的敏感数据");
          String filterWords = CommonUtils.readFile(fileName);
          return filterWords;
        });
  }

  public static CompletableFuture<String[]> spiltFuture(String context) {
    return CompletableFuture.supplyAsync(
        () -> {
          CommonUtils.printThreadLog("敏感数据转换成数组");
          String[] wordsArray = context.split(",");
          return wordsArray;
        });
  }
}
