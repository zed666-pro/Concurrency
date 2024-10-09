package StructuredConcurency.StructureDemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * @Author shengaojie @Date 2023/4/13 21:14 @ClassName: StructureDemo02 @Description: TODO @Version
 * 1.0
 */
public class StructureDemo02 {
  public StructureDemo02() {}

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      //      System.out.println(Thread.currentThread().getName());
      // 一个任务失败后，就会取消剩余任务的执行

      Future<String> future1 =
          scope.fork(
              () -> {
                int i = 1 / 0;
                System.out.println("print task1");
                return "future1";
              });

      Future<String> future2 =
          scope.fork(
              () -> {
                Thread.sleep(300);
                System.out.println("print task2");
                return "future2";
              });
      Future<String> future3 =
          scope.fork(
              () -> {
                Thread.sleep(300);
                System.out.println("print task3");
                return "future3";
              });
      scope.join();
      scope.throwIfFailed();
      System.out.println(future1.resultNow());
      System.out.println(future2.resultNow());
      System.out.println(future3.resultNow());
    }
  }
}
