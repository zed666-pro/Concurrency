package asynchronous.completableFuture.thenCombine;

import asynchronous.kit.CommonUtils;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/15 09:46
 * @ClassName: ThenCombineFuture
 * @Description: TODO
 * @Version 1.0
 */
public class ThenCombineFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<String[]> future = pool.submit(() -> {
            CommonUtils.printThreadLog("读取敏感词汇并解析");
            String context = CommonUtils.readFile("demo/filter_words.txt");
            String[] words = context.split(",");
            return words;

        });

        Future<String> future1 = pool.submit(() -> {
            CommonUtils.printThreadLog("读取news文件中的内容");
            String context = CommonUtils.readFile("demo/news.txt");
            return context;

        });

        Future<String> future2 = pool.submit(() -> {
            String[] words = future.get();
            String context = future1.get();
            for (String word : words) {
                if (context.indexOf(word) > -1) {
                    context = context.replace(word, "**");
                }
            }
            return context;
        });
        System.out.println("替换后的结果为：" + future2.get());

        pool.submit(() -> {
            try {
                String[] words = future.get();
                String context = future1.get();
                for (String word : words) {
                    if (context.indexOf(word) > -1) {
                        context = context.replace(word, "**");
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
