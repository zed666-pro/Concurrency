package Thread.CountDownLatch;

import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


/**
 * @Author shengaojie @Date 2023/5/11 11:01 @ClassName: RefactorDemo01 @Description:
 * 可以自己定义策略 @Version 1.0
 */
public class RefactorDemo01 extends StructuredTaskScope {
    private final List<String> results = new ArrayList();

    public RefactorDemo01() {
        super(null, Thread.ofVirtual().factory());
    }

    @Override
    protected void handleComplete(Future future) {
        if (future.state() == Future.State.SUCCESS && results.size() < 2) {
            String result = (String) future.resultNow();
            results.add(result);
        }
    }

    public List<String> getResults() throws InterruptedException {
        System.out.println(results.size());

        return results;
    }

    @Test
    public void test01() throws InterruptedException {

        try (var scope = new RefactorDemo01()) {
            Future fork1 =
                    scope.fork(
                            () -> {
                                Thread.sleep(1000);
                                return "task1";
                            });

            Future fork2 =
                    scope.fork(
                            () -> {
                                Thread.sleep(2000);
                                return "task2";
                            });

            Future fork3 =
                    scope.fork(
                            () -> {
                                Thread.sleep(400);
                                return "task3";
                            });

            scope.join();

            List<String> results = scope.getResults();
            System.out.println(results);
        }
    }
}
