package StructuredConcurency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * @Author shengaojie @Date 2023/4/12 14:01 @ClassName: ShutdownOnSuccessDemo @Description:
 * TODO @Version 1.0
 */
public class ShutdownOnSuccessDemo {
  public static void main(String[] args) throws InterruptedException, ExecutionException {

    var scope = new StructuredTaskScope.ShutdownOnSuccess<String>();

    Future<String> future1 =
        scope.fork(
            () -> {
              Thread.sleep(2000);
              System.out.println(1);
              return "Task1";
            });
    System.out.println("continue");
    Future<String> future2 =
        scope.fork(
            () -> {
              Thread.sleep(3000);
              System.out.println(2);
              return "Task 2";
            });
    System.out.println("continue1");

    Future<String> future3 =
        scope.fork(
            () -> {
              Thread.sleep(4000);
              System.out.println(3);
              return "Task 3";
            });

    scope.join(); // Join both forks
    System.out.println("continue2");

    System.out.println(scope.result());
  }
}
