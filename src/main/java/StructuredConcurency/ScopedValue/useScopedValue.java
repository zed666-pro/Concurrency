package StructuredConcurency.ScopedValue;

import jdk.incubator.concurrent.ScopedValue;


/**
 * @Author shengaojie
 * @Date 2023/4/14 16:46
 * @ClassName: useScopedValue
 * @Description: TODO
 * @Version 1.0
 */
public class useScopedValue {
    public static final ScopedValue value = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                ScopedValue.where(value,Thread.currentThread().getName(),() -> {
                    System.out.println(Thread.currentThread().getName()+ "  获取到的线程名称为：" + value.get());
                });
            }).start();

        }
    }
}
