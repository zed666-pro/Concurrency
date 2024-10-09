package Lock.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class tryLock {
    private static  ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("尝试获取锁");
            try {
                if (!lock.tryLock(1,TimeUnit.SECONDS)) {
                    System.out.println("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                System.out.println("获取到了锁");
            }finally {
                lock.unlock();
            }
        });

        lock.lock();
        System.out.println("main线程获得到了锁");
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        lock.unlock();
    }
}
