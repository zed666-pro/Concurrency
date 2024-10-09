package Thread.ThreadPool.defineMyThreadpool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestPool {


    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(1, 3, TimeUnit.SECONDS, 1,(queue,task) -> {
            //1.死等
            //queue.put(task);
            //2.带超时的等待
            queue.offer(task,1,TimeUnit.SECONDS);
            //3.放弃任务的执行
            //System.out.println("放弃任务");
            //4.抛出异常
            //throw new RuntimeException();
            //5.让调用这自己执行任务
           //task.run();

        });
        for (int i = 0; i < 3; i++) {
            int j = i;
            pool.excute(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()  + ":" + j) ;
            });
        }


    }
}
class ThreadPool{
    private BlockingQueue<Runnable> taskQueue;

    private HashSet<Worker> workers = new HashSet();

    //核心的线程数
    private int coreSize;

    //获取任务的超时时间
    private long timeout;

    private TimeUnit unit;

    private RejectPolicy<Runnable> rejectPolicy;

    public void excute(Runnable task){
//      当任务数没有超过coreSize时，直接交给worker对象执行
        //当任务的数量超过coreSize时，加入到任务队列中去
        synchronized (workers){
            if(workers.size() < coreSize){

                //将task作为参数传给worker的构造方法
                Worker worker = new Worker(task);
                System.out.println("新增 worker:"+ worker + "，"+task);
                workers.add(worker);
                worker.start();

            }else{
                System.out.println("将任务 "+task+" 加入任务队列");
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    public ThreadPool(int coreSize, long timeout, TimeUnit unit,int queueCapcity,RejectPolicy<Runnable> policy) {
        this.taskQueue = new BlockingQueue<>(queueCapcity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.rejectPolicy = policy;


    }

    class Worker extends Thread{

        Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {

            //1.当任务不为空的时候，执行任务
            //2.如果task为空，在接着从任务队列中获取任务
            while(task != null || (task = taskQueue.poll(timeout,unit)) != null){
                try{
                    System.out.println("正在执行。。。" + task);
                    task.run();

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }

            }
            //任务执行完成之后，从workers中移除
            synchronized (workers){
                System.out.println("worker 被移除 ：" + this);
                workers.remove(this);
            }

        }
    }
}

class BlockingQueue<T>{

    private Deque<T> queue = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullWaitset = lock.newCondition();

    private Condition emptyWaitset = lock.newCondition();

    private int queueCapcity;

    public BlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    private int capcity;

    //阻塞获取
    public T take(){
        lock.lock();
        try{
            while(queue.isEmpty()){
                emptyWaitset.await();
            }
          T t =  queue.removeFirst();
            fullWaitset.signal();
            return t;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    //带有超时的获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();

        try{
            //将超时时间转换成纳秒
            long nanos = unit.toNanos(timeout);
            while(queue.isEmpty()){
                if(nanos < 0)
                    return null;
                //返回的是剩余的时间
                nanos = emptyWaitset.awaitNanos(nanos);
            }
            return queue.removeFirst();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void put(T element){
        lock.lock();

        try{
            while(queue.size() == capcity){
                System.out.println("等待加入任务队列 " + element);
                fullWaitset.await();
            }
            queue.addLast(element);
            emptyWaitset.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    //带超时的时间阻塞添加
    public boolean offer(T task,long timeout,TimeUnit unit){
        lock.lock();

        try{
            long nanos = unit.toNanos(timeout);
            while(queue.size() == capcity){

                System.out.println("等待加入任务队列 " + task);
                if (nanos < 0){
                    return false;
                }
               nanos = fullWaitset.awaitNanos(nanos);
            }
            queue.addLast(task);
            emptyWaitset.signal();
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public int size(){
        lock.lock();
        try{
            return queue.size();
        }finally {
            lock.unlock();
        }

    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();

        try{
            //判断队列是否已满
            if(queue.size() == capcity){
                rejectPolicy.reject(this,task);
            }else{
                System.out.println("加入任务队列 ：" + task);
                queue.addLast(task);
                emptyWaitset.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T>{

    void reject(BlockingQueue<T> queue,T task);
}
