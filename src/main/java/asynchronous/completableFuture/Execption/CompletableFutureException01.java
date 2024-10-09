package asynchronous.completableFuture.Execption;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author shengaojie
 * @Date 2023/4/15 11:56
 * @ClassName: CompletableFutureException01
 * @Description: TODO
 * @Version 1.0
 */

@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)   // 预热次数和时间
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class CompletableFutureException01 {

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void testException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int i = 1/0;
            return "task1";
        }).exceptionally(ex -> {
            if (ex instanceof InterruptedException){
                System.out.println("中断异常");
                throw new RuntimeException(ex);
            }

            if(ex instanceof RuntimeException){
                System.out.println("发生了运行时异常");
                throw new RuntimeException(ex);
            }
            return null;
        });

        String join = future.join();
        System.out.println(join);
    }
    public static void main(String[] args) throws RunnerException {
        org.openjdk.jmh.runner.options.Options opt = new OptionsBuilder()
                .include(CompletableFutureException01.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
