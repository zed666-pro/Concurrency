package thread_.method;

import java.util.concurrent.TimeUnit;

/**
 * @description: 设置守护线程
 * @author: shengaojie
 * @create: 2023-11-15
 **/

public class SetDaemon {
    public static void main(String[] args) throws InterruptedException {
        // 如果不将thread设置为守护线程，在main线程结束之后，程序不会退出
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        thread.setDaemon(true);   // 设置thread为守护线程
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("main线程结束了.....");
    }


}
