package StructuredConcurency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author shengaojie
 * @Date 2023/4/12 13:59
 * @ClassName: InvokeAnyDemo
 * @Description: TODO
 * @Version 1.0
 */
public class InvokeAnyDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {

                int i = 1/ 0 ;
                System.out.println(1);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(600);
                System.out.println(2);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(600);
                System.out.println(3);
                return "Task 3";
            }
        });

        callables.add(() -> {
          return "task4";
        });
        String result = executorService.invokeAny(callables);
        executorService.shutdown();
    }
}
