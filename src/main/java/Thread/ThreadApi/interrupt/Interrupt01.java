package Thread.ThreadApi.interrupt;

import java.util.concurrent.TimeUnit;

public class Interrupt01 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("i am be interrupted....");
            }
        });

        thread.start();
        Thread.sleep(200);
        thread.interrupt();
    }
}
