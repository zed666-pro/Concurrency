package Lock.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

       lock.lock();
       try{
           System.out.println("enter main");
           m1();
       }finally {
           lock.unlock();
       }

    }
    public static void m1(){
        lock.lock();
        try {
            System.out.println("enter m1");
            m2();
        }finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try {
            System.out.println("enter m2");
        }finally {
            lock.unlock();
        }
    }
}
