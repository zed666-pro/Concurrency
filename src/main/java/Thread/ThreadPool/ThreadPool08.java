package Thread.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool08 {
    public static void main(String[] args) {
        int taskNum = 4;
        RejectedHandler rejectedHandler = new RejectedHandler();
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        executorService.setRejectedExecutionHandler(rejectedHandler);
        System.out.println("当前执行器将要处理"+taskNum+"个任务");
        for (int i = 0; i < taskNum; i++) {
            executorService.submit(new Task("任务"+i));
        }
        System.out.println("执行器即将关闭");
        //执行了shutdown
        executorService.shutdown();
        System.out.println("一个新的任务即将被提交到执行器");
        executorService.submit(new Task("新任务"));
    }
}

class RejectedHandler implements RejectedExecutionHandler{
    @Override
    public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
        System.out.println("执行器的终止状态为："+arg1.isTerminated());
        System.out.println("任务"+arg0.toString()+"的执行被执行器拒绝");
    }
}

class Task implements Runnable{
    String name;
    Task(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("工作"+name+"开始运行");
        int time = (int) (Math.random() * 100);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前工作"+name+"结束了");
    }

    public String toString(){
        return name;
    }
}
