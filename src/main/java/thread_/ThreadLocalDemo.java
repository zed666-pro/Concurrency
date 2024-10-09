package thread_;

import org.junit.jupiter.api.Test;

/**
 * @description: ThreadLocal
 * @author: shengaojie
 * @create: 2024-08-24
 **/

public class ThreadLocalDemo {
    static ThreadLocal<String> local = new ThreadLocal();

    void print() {
        System.out.println(Thread.currentThread().getName() + " str: " + local.get());
        local.remove();
        System.out.println(Thread.currentThread().getName() + " str: " + local.get());

    }

    @Test
    public void testUseOfThreadLocal() {
        /**
         * t1中只能获取到t1中set的值
         */
        new Thread(() -> {
            local.set("zhangsan");
            print();
        }, "t1").start();

        new Thread(() -> {
            local.set("list");
            print();
        }, "t2").start();
    }


    //ThreadLocal无法继承：父线程中设置的值，在子线程中是没法获取的
    @Test
    public void testInherit() {
        local.set("father");
        new Thread(() -> {
            //子线程中无法获取父线程中的值
            System.out.println("子线程中获取父线程中的值：" + local.get());
        }).start();

        //解决方法1：子线程中自己设置值
        new Thread(() -> {
            local.set("father");
            System.out.println("子线程中获取父线程中的值：" + local.get());

        }).start();


        //解决方案2：使用InheritableThreadLocal
        ThreadLocal<String> inherit = new InheritableThreadLocal<>();
        inherit.set("mother");
        new Thread(() -> {
            System.out.println("子线程通过InheritableThreadLocal获取父线程中的值：" + inherit.get());
        }).start();
    }
}
