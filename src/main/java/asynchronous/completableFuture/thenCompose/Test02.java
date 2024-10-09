//package asynchronous.completableFuture.thenCompose;
//
//import jdk.incubator.concurrent.StructuredTaskScope;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
///**
// * @Author shengaojie
// * @Date 2023/4/12 10:34
// * @ClassName: Test02
// * @Description: TODO
// * @Version 1.0
// */
//public class Test02 {
//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//
//            System.out.println(Thread.currentThread());
//            Future<String> future1  = scope.fork(() ->{
//                Thread.sleep(200);
//                System.out.println(1);
//                return "Task 1";
//            });
//            Future<String> future2 = scope.fork(() -> {
//                Thread.sleep(300);
//                System.out.println(2);
//                return "Task 2";
//            });
//
//
//            Future<String> future3 = scope.fork(() -> {
//                Thread.sleep(400);
//
//                System.out.println(3);
//                return "Task 3";
//            });
//
//            scope.join();           // Join both forks
//
//
//        }
//    }
//}
