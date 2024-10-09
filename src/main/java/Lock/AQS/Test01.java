package Lock.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Test01 {

    public static void main(String[] args) {
        TestAQS aqs = new TestAQS();

        new Thread(()->{
            aqs.lock();
            try{
                TimeUnit.SECONDS.sleep(1);
                System.out.println("111111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("2222222");
                aqs.unlock();
            }
        }).start();


        new Thread(()->{
            aqs.lock();
            try {
                System.out.println("33333333");
            }finally {
                System.out.println("444444444");
                aqs.unlock();
            }

        }).start();
    }


}

class TestAQS implements Lock {

    private Sync sync = new Sync();


    class Sync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
           if (compareAndSetState(0,1)){
               setExclusiveOwnerThread( Thread.currentThread());
               return true;
           }
           return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return sync.newCondition();
        }
    }

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
        return sync.tryAcquireNanos(1,unit.toNanos(time));
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
