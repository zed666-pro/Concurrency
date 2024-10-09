package Thread.PrincipleOfThreadPool;

@FunctionalInterface
public interface DenyPolicy {
    void reject(Runnable runnable, ThreadPool threadPool) throws RunnableDenyException;


    class DiscardDenyPolicy implements DenyPolicy{
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            // do nothing
        }
    }

    //该拒绝策略回想任务提出者抛出异常
    class AbortDenyPolicy implements DenyPolicy{
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) throws RunnableDenyException {
            throw new RunnableDenyException("The runnable" + runnable + "will be abort");
        }
    }

    //该拒绝策略会让任务在调用这线程上执行
    class RunnerDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool){
            if(!threadPool.isShutdown()){
                runnable.run();
            }
        }
    }


}
