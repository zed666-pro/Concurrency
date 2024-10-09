package StructuredConcurency;


import org.junit.Test;

/**
 * @Author shengaojie
 * @Date 2023/4/16 14:35
 * @ClassName: ThreadLocalProblem01
 * @Description: TODO
 * @Version 1.0
 */
public class ThreadLocalProblem01 {
    public static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {

        local.set("hello world");
        new Thread(() -> {
            // 父线程中设置的value值子线程中获取不到
            String value = local.get();
            System.out.println("local is: " + value);

        }).start();
        local.remove();
    }

    public static InheritableThreadLocal<String> inheritableLocal = new InheritableThreadLocal();

    @Test
    public void test1() {
        inheritableLocal.set("hello world");
        new Thread(() -> {
            String value = inheritableLocal.get();
            System.out.println("local is:" + value);
        }).start();

        inheritableLocal.remove();
    }
}
