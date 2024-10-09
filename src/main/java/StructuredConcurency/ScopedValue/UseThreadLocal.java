package StructuredConcurency.ScopedValue;

/**
 * @Author shengaojie
 * @Date 2023/4/14 16:46
 * @ClassName: UseThreadLocal
 * @Description: TODO
 * @Version 1.0
 */
public class UseThreadLocal {
    private  static ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
        // 重写这个方法，可以修改“线程变量”的初始值，默认是null
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        // 创建一号线程
        new Thread(() -> {
            num.set(1);
            System.out.println("一号线程中ThreadLocal变量中保存的值为：" + num.get());
        }).start();

        // 创建二号线程
        new Thread(() -> {
            num.set(2);
            System.out.println("二号线程中ThreadLocal变量中保存的值为：" + num.get());
        }).start();

        //为了让一二号线程执行完毕，让主线程睡500ms
        Thread.sleep(500);
        System.out.println("主线程中ThreadLocal变量中保存的值：" + num.get());
    }

}

