package asynchronous.completableFuture.thenCombine;

import asynchronous.kit.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author shengaojie @Date 2023/4/15 09:42 @ClassName: ThenCombineDemo @Description: TODO @Version
 * 1.0
 */
public class ThenCombineDemo {
  public static void main(String[] args) throws Exception {

    ExecutorService pool = Executors.newFixedThreadPool(4);
    // 读取敏感词汇的文件并解析到数组中
    CompletableFuture<String[]> future1 =
        CompletableFuture.supplyAsync(
            () -> {
              CommonUtils.printThreadLog("读取敏感词汇并解析");
              String context = CommonUtils.readFile("demo/filter_words.txt");
              String[] words = context.split(",");
              return words;
            },
            pool);
    // 读取news文件内容
    CompletableFuture<String> future2 =
        CompletableFuture.supplyAsync(
            () -> {
              CommonUtils.printThreadLog("读取news文件内容");
              String context = CommonUtils.readFile("demo/news.txt");
              return context;
            },
            pool);

    CompletableFuture<String> combinedFuture =
        future1.thenCombineAsync(
            future2,
            (words, context) -> {
              // 替换操作
              CommonUtils.printThreadLog("替换操作");
              for (String word : words) {
                if (context.indexOf(word) > -1) {
                  context = context.replace(word, "**");
                }
              }
              return context;
            },
            pool);
    String filteredContext = combinedFuture.get();
    pool.shutdown();
  }
}
