package Thread.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool02 {
    public static void main(String[] args) {
        int workerNum = 5;
        Employ employ = new Employ();
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker("工人" + (i + 1));
            employ.dispatch(worker);
        }
        employ.endwork();
    }
}

class Worker implements Runnable{


    public Worker(String name){
        Thread.currentThread().setName(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                "正在努力工作");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Employ{

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public void endwork(){
        executor.shutdown();
    }

    public void dispatch(Worker worker){
        System.out.println("雇主正在派遣工人到工作岗位上");
        executor.execute(worker);
        System.out.println("活动的线程数为："+executor.getActiveCount());
        System.out.println("线程池的大小为："+executor.getPoolSize());
    }
}
