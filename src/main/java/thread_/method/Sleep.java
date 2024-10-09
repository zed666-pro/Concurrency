package thread_.method;


import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @description: null
 * @author: shengaojie
 * @create: 2023-11-15
 **/

public class Sleep {

    @Test
    public void test04() {
        // sleep 是一个静态方法 Thread.sleep()
        // 当前线程进入指定时间的休眠，休眠不会放弃monitor锁的所有权
        // 推荐使用TimeUnit来代替Thread.sleep的使用
        // 休眠3小时24分17秒88毫秒的写法
        // 1. 使用sleep
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(12257088L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        // 2. 使用TimeUnit
        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(3);
                TimeUnit.MINUTES.sleep(24);
                TimeUnit.SECONDS.sleep(17);
                TimeUnit.MILLISECONDS.sleep(88);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

    }
}
