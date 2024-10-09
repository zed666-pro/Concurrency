package Thread.PrincipleOfThreadPool.core;

import Thread.PrincipleOfThreadPool.*;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends  Thread  implements ThreadPool {

    private final int initSize;

    private final int maxSize;


    private final int coreSize;

    private int activeCount;

    private final ThreadFactory threadFactory;

    private final RunnableQueue runnableQueue;

    private volatile boolean isShutdown = false;

    private final ArrayDeque<ThreadTask> threadQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;


    public BasicThreadPool(int initSize, int maxSize, int coreSize,int queueSize) {

         this(initSize,maxSize,coreSize,DEFAULT_DENY_POLICY,queueSize,DEFAULT_THREAD_FACTORY,10,TimeUnit.SECONDS);
    }

    public BasicThreadPool(int initSize, int maxSize, int coreSize, DenyPolicy defaultDenyPolicy, int queueSize, ThreadFactory threadFactory, int keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize,defaultDenyPolicy,this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    private void init(){

        start();

        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    //提交任务

    @Override
    public void execute(Runnable runnable) throws RunnableDenyException {
        if(this.isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        this.runnableQueue.offer(runnable);
    }

    private void newThread(){
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);

        ThreadTask threadTask = new ThreadTask(thread, internalTask);

        this.activeCount++;
        thread.start();
    }

    private void removeThread(){
        ThreadTask threadTask = threadQueue.remove();
    }

    @Override
    public void run() {
        while (! isShutdown && !isInterrupted()){
            try{
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
                break;
            }

            synchronized (this){
                if(isShutdown){
                    break;
                }

                if (runnableQueue.size() > 0 && activeCount < coreSize){
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    continue;
                }
                if (runnableQueue.size() == 0 && activeCount > coreSize){
                    for (int i = coreSize; i < activeCount; i++) {
                        removeThread();
                    }
                }
            }
        }
    }


    private static class ThreadTask{
        Thread thread;
        InternalTask internalTask;
        public ThreadTask(Thread thread,InternalTask internalTask){
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    @Override
    public void shutdown() {
        synchronized (this){
            if (isShutdown) return;
            isShutdown = true;
            threadQueue.forEach(threadTask ->{
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (isShutdown)
            throw new IllegalStateException("The thread pool is destory");
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown)
            throw new IllegalStateException("The thread pool is destory");
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown)
            throw new IllegalStateException("The thread pool is destory");
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutdown)
            throw new IllegalStateException("The thread pool is destory");
        return runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        if (isShutdown)
            throw new IllegalStateException("The thread pool is destory");
        return this.activeCount;
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }


    private static class DefaultThreadFactory implements ThreadFactory{
        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"+ GROUP_COUNTER.getAndDecrement());

        private static final AtomicInteger COUNTER = new AtomicInteger(0);


        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group,runnable,"thread-poll"+COUNTER.getAndDecrement());
        }
    }
}

