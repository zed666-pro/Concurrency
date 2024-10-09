package Thread.ThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPool04 {
  public static void main(String[] args) {
    int stuNum = 8;
    List<Student1> list = new ArrayList<Student1>();
    TestRoom1 testRoom1 = new TestRoom1();
    for (int i = 0; i < stuNum; i++) {
      Student1 student1 = new Student1("学生" + (i + 1));
      testRoom1.test(student1);
      list.add(student1);
    }

    for (Student1 stu1 : list) {
      System.out.println(stu1.name + "考试用时为：" + testRoom1.getTime(stu1));
    }

    testRoom1.endTest();
  }
}

class Student1 implements Callable<Long> {
  String name;

  public Student1(String name) {
    this.name = name;
  }

  @Override
  public Long call() throws Exception {
    System.out.println(name + "开始考试");
    long start = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++)
      ;

    long end = System.currentTimeMillis();
    return end - start;
  }
}

class TestRoom1 {

  ExecutorService testPool;
  Map<Student1, Future<Long>> stuToTimeMap;

  TestRoom1() {
    testPool = Executors.newFixedThreadPool(4);
    stuToTimeMap = new HashMap<Student1, Future<Long>>();
  }

  public void test(Student1 student1) {
    Future<Long> result = testPool.submit(student1);
    stuToTimeMap.put(student1, result);
  }

  public Long getTime(Student1 student1) {
    Future<Long> longFuture = stuToTimeMap.get(student1);

    try {
      Long result = longFuture.get();
      return result;
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void endTest() {
    if (!testPool.isShutdown()) testPool.shutdown();
  }
}
