package Thread.PrincipleOfThreadPool.core;

import Thread.PrincipleOfThreadPool.DenyPolicy;
import Thread.PrincipleOfThreadPool.RunnableDenyException;
import Thread.PrincipleOfThreadPool.RunnableQueue;
import Thread.PrincipleOfThreadPool.ThreadPool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {

    private final int limit;

    private final DenyPolicy denyPolicy;

    private final LinkedList<Runnable> runnableLinkedList = new LinkedList<>();

    private ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) throws RunnableDenyException {
        synchronized (runnableLinkedList){
            if(runnableLinkedList.size() > limit){
                denyPolicy.reject(runnable,threadPool);
            }else{
                runnableLinkedList.addLast(runnable);
                runnableLinkedList.notifyAll();
            }
        }
    }


    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableLinkedList){
            while (runnableLinkedList.isEmpty()){
                try{
                    runnableLinkedList.wait();
                }catch (InterruptedException e){
                    throw e;
                }
            }
        }
        return runnableLinkedList.removeFirst();
    }

    @Override
    public int size() {
        synchronized (runnableLinkedList){
            return runnableLinkedList.size();
        }
    }
}
