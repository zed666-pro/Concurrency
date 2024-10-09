package Thread.PrincipleOfThreadPool;

public interface ThreadPool {
    //提交任务到线程池
    void execute(Runnable runnable) throws RunnableDenyException;

    //关闭线程池
    void shutdown();

    //获取线程池的初始化大小
    int getInitSize();

    //获取线程池的最大线程数
    int getMaxSize();

    //获取线程池的核心线程数量
    int getCoreSize();

    //获取线程池中用于缓存队列的大小
    int getQueueSize();


    //获取线程池中活跃的线程数量
    int getActiveCount();

    // 判断线程池是否被关闭了
    boolean isShutdown();
}
