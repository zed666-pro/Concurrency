package StructuredConcurency;

import jdk.incubator.concurrent.StructuredTaskScope;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @description: 比较invokeAll和结构化并发的性能
 * @author: shengaojie
 * @create: 2023-11-01
 **/

@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)   // 预热次数和时间
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class InvokeAllVSOnFailure {

    //@BenchmarkMode(Mode.Throughput)
    //@OutputTimeUnit(TimeUnit.SECONDS)
    //@Benchmark
    public void testFixedPool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Callable<String>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(() -> {
                TimeUnit.MILLISECONDS.sleep(50);
                return "task complete";
            });
        }

        List<Future<String>> futures = pool.invokeAll(list);
        for (Future<String> future : futures) {
            String s = future.get();
        }

        pool.shutdown();
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void testCachePool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ArrayList<Callable<String>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                return "task complete";
            });
        }

        List<Future<String>> futures = pool.invokeAll(list);
        for (Future<String> future : futures) {
            String s = future.get();
        }

        pool.shutdown();
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void testStructureOnSuccess() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                futures.add(
                        scope.fork(() -> {
                                    TimeUnit.MILLISECONDS.sleep(500);
                                    return "task complete";
                                }
                        )
                );
            }
            scope.join();
            scope.throwIfFailed();

            for (Future<String> future : futures) {
                String s = future.resultNow();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) throws RunnerException {
        org.openjdk.jmh.runner.options.Options opt = new OptionsBuilder()
                .include(InvokeAllVSOnFailure.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
