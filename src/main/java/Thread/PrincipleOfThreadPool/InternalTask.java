package Thread.PrincipleOfThreadPool;

public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;

    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为running并且没有中断，则其将不断第从queue中获取runnable，然后
        //执行run方法
        while (running && !Thread.currentThread().isInterrupted()) {

            try {
                Runnable task = runnableQueue.take();
                task.run();

            } catch (InterruptedException e) {
                running = false;
                break;
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
