package Thread.PrincipleOfThreadPool;

@FunctionalInterface
public interface ThreadFactory {

    Thread createThread(Runnable runnable);
}
