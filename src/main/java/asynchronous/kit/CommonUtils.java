package asynchronous.kit;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;


/**
 * @Author shengaojie
 * @Date 2023/4/11 16:08
 * @ClassName: CommonUtils
 * @Description: TODO
 * @Version 1.0
 */

public class CommonUtils {

    public static String readFile(String pathToFile) {
        try {
            return Files.readString(Paths.get(pathToFile));
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void sleepMillis(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepSecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printThreadLog(String message) {
        String result = new StringJoiner(" | ")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.format("%2d",Thread.currentThread().getId()))
                .add(String.valueOf(Thread.currentThread().getName()))
                .add(message)
                .toString();
        System.out.println(result);
    }
}
