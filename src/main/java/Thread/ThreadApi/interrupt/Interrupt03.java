package Thread.ThreadApi.interrupt;

import java.util.concurrent.TimeUnit;

public class Interrupt03 {
    public static void main(String[] args) throws InterruptedException {
       Thread thread = new Thread(){
           @Override
           public void run() {
               while (true){
                   try {
                       TimeUnit.SECONDS.sleep(1);
                   } catch (InterruptedException e) {
                       System.out.println("i am be interrupted... ?" + this.isInterrupted());
                   }
               }
           }
        };

        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println("Thread is interrupted ?" + thread.isInterrupted());
        thread.interrupt();
        TimeUnit.MILLISECONDS.sleep(2);

        System.out.println("Thread is interrupted ?" + thread.isInterrupted());

    }
}

