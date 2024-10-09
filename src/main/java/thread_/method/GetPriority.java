package thread_.method;


import org.junit.jupiter.api.Test;

/**
 * @description: null
 * @author: shengaojie
 * @create: 2023-11-15
 **/

public class GetPriority {

    public void test01() {
        // 线程的优先级设置
        // 1. 理论上优先级高的线程被cpu执行的可能越大
        // 2. 但是在cpu处于空闲的状态下，线程的优先级基本没有作用
        // 3. 线程的优先级的范围是1~10，如果没有设置优先级默认和所在的线程组使用一样的
        //   优先级，一般是5

        // 测试不同优先级的线程被cpu执行的可能性
        Thread thread1 = new Thread(() -> {
            while (true)
                System.out.println("我的优先级是10");
        });

        Thread thread2 = new Thread(() -> {
            while (true)
                System.out.println("我的优先级是3");
        });

        thread1.setPriority(10);
        thread2.setPriority(3);

        thread1.start();
        thread2.start();


    }

    @Test
    public void test02() {
        // 验证线程的优先级和线程组的关系
        Thread thread = new Thread("t1");

        System.out.println(thread.getName() + "优先级为： " + thread.getPriority());
        Thread thread1 = new Thread(() -> {
            Thread thread2 = new Thread("t3");
            System.out.println(thread2.getName() + "优先级为： " + thread2.getPriority());
        }, "t2");

        thread1.setPriority(8);
        thread1.start();
        System.out.println(thread1.getName() + "优先级为： " + thread1.getPriority());
    }

}
