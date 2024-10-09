package asynchronous.completableFuture;

import asynchronous.kit.CommonUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author shengaojie
 * @Date 2023/4/11 16:11
 * @ClassName: FutureDemo
 * @Description: 传统Future中存在的问题
 * @Version 1.0
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        // step 1: 读取敏感词汇
        Future<String[]> filterWordFuture = executor.submit(() -> {
            String str = CommonUtils.readFile("demo/filter_words.txt");
            String[] filterWords = str.split(",");
            return filterWords;
        });

        // step 2: 读取新闻稿文件内容
        Future<String> newsFuture = executor.submit(() -> {
            return CommonUtils.readFile("demo/news.txt");
        });

        // step 3: 替换操作(当敏感词汇很多，文件很多，替换也会是个耗时的任务)
        Future<String> replaceFuture = executor.submit(() -> {
            String[] words = filterWordFuture.get();
            String news = newsFuture.get();

            // 替换敏感词汇
            for (String word : words) {
                if (news.indexOf(word) >= 0) {
                    news = news.replace(word, "**");
                }
            }
            return news;
        });

        String filteredNews = replaceFuture.get();
        System.out.println("过滤后的新闻稿:" + filteredNews);

        executor.shutdown();
    }
}
