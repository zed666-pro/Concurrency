package Thread.PrincipleOfThreadPool;

public interface RunnableQueue {

    //当有新任务的时候，可以用该方法加入到队列中去
    void offer(Runnable runnable) throws RunnableDenyException;

    //工作线程通过take方法获取到
    Runnable take() throws InterruptedException;

    int size();
}
