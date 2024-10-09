package Thread.Exception;

import java.util.concurrent.TimeUnit;

/**
 * @author shengaojie
 */
public class UncaughtExceptionHandler {
  public static void main(String[] args) {

    // 设置回调接口
    Thread.setDefaultUncaughtExceptionHandler(
        (t, e) -> {
          System.out.println(t.getName() + "  occur exception");
          e.printStackTrace();
        });

    Thread thread =
        new Thread(
            () -> {
              try {
                TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }

              System.out.println(1 / 0);
            },
            "test-Thread");

    thread.start();
  }
}
