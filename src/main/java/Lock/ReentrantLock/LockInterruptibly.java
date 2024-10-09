package Lock.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("没有获取到锁返回");
                return;
            }


            try{
                System.out.println("获取到锁");
            }finally {
                lock.unlock();
            }
        });

        lock.lock();
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("打断thread");
        thread.interrupt();
    }
}
