package Thread.ThreadApi.interrupt;

public class Interrupt02 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            while (true){

            }
        });

        thread.start();

        Thread.sleep(2000);
        System.out.println("Thread is interrupted ?" + thread.isInterrupted());
        thread.interrupt();
        System.out.println("Thread is interrupted ?"+ thread.isInterrupted());

    }
}
