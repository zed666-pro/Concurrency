package Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DefineLock {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();
  
        new Thread(() -> {
            myLock.lock();
            System.out.println("11111111111");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("22222222222");
            myLock.unlock();
        }).start();

        new Thread(() -> {
            myLock.lock();
            System.out.println("3333333333");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4444444444");
            myLock.unlock();
        }).start();


    }
}


class MyLock implements Lock {

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            //如果修改成功返回true，否则返回false
            if (compareAndSetState(0, 1)) {
                //设置当前的线程为锁的持有者
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //状态为0无序解锁
            if (getState() == 0)
                throw new IllegalStateException();

            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            //0表示没有上锁，1表示占用锁
            return getState() == 1;
        }


        protected Condition newCondition() {
            return new ConditionObject();
        }


    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


}


