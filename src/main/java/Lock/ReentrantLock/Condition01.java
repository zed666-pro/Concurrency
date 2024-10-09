package Lock.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition01 {
    private static ReentrantLock Room = new ReentrantLock();
    static boolean hasCigarette = false;
    static boolean hasTakeOut = false;

    //等待的吸烟室
    static Condition waitCigarette = Room.newCondition();
    static Condition waitTakeOut = Room.newCondition();

    //等待的外卖室
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            Room.lock();
            System.out.println("烟送到没：" + hasCigarette);
            while (!hasCigarette) {
                try {
                    //如果烟没有送到，就在吸烟室内等待
                    waitCigarette.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                System.out.println("烟送到了，开始工作了");
            } finally {
                Room.unlock();
            }
        }).start();


        new Thread(() -> {
            Room.lock();
            System.out.println("外卖送到没：" + hasTakeOut);
            while (!hasTakeOut) {
                try {
                    //如果烟没有送到，就在吸烟室内等待
                    waitTakeOut.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                System.out.println("外卖送到了，开始工作");
            } finally {
                Room.unlock();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);

        //送烟的线程
        new Thread(() -> {
            Room.lock();
            try {
                hasCigarette = true;
                waitCigarette.signal();
            } finally {
                Room.unlock();
            }


        }).start();
        //送外卖的线程
        new Thread(() -> {
            Room.lock();
            try {
                hasTakeOut = true;
                waitTakeOut.signal();
            } finally {
                Room.unlock();
            }
        }).start();
    }
}
