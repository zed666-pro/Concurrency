package StructuredConcurency.my_structured_concurrency;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * @Author shengaojie @Date 2023/9/4 09:30 @ClassName: My_scope @Description: TODO @Version 1.0
 */
public class MyScope<T> extends StructuredTaskScope<T> {

  private final Queue<Future<T>> results = new ConcurrentLinkedQueue<>();

  @Override
  protected void handleComplete(Future<T> future) {
    if (future.isDone()) {
      results.add(future);
    }
  }

  // Returns a stream of results from the subtasks that completed successfully
  public Queue<Future<T>> results() {
    return results;
  }
}
