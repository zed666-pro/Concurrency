package Thread.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool03 {
    public static void main(String[] args) {
        int stuNum = 10;
        TestRoom testRoom = new TestRoom();
        for (int i = 0; i < stuNum; i++) {
            Student student = new Student("student:" + (i + 1));
            testRoom.test(student);
        }
        testRoom.endTest();
    }
}

class TestRoom{
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    public void test(Student student){
            executorService.execute(student);
    }

    public void endTest(){
        if (!executorService.isShutdown())
            executorService.shutdown();
    }
}

class Student implements Runnable{

    String name;

    public Student(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("考试开始："+name);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++);
        long end = System.currentTimeMillis();
        System.out.println("考生答题完毕，用时为："+(end - start)+"毫秒");

    }
}
