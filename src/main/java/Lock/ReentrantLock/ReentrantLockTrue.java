package Lock.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTrue {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        for (int i = 0; i < 100; i++) {
            //100个线程在这里阻塞
            new Thread(()->{
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "  is running.....");
                }finally {
                    lock.unlock();
                }

            },"t:"+i).start();
        }

        TimeUnit.SECONDS.sleep(1);
        lock.unlock();

    }
}
