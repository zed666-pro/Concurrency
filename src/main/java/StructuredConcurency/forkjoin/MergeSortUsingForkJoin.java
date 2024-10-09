package StructuredConcurency.forkjoin;


import StructuredConcurency.forkjoin.task.MergeSortTask;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author shengaojie
 * @Date 2023/6/1 16:58
 * @ClassName: MergerSortForkjoin
 * @Description: TODO
 * @Version 1.0
 */
public class MergeSortUsingForkJoin {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        int[] array = MergeSort.getArray();
        ForkJoinPool pool = new ForkJoinPool();
        MergeSortTask task = new MergeSortTask(array, 0, array.length);
        int[] sortedArray = pool.invoke(task);

        System.out.println("Sorted array: " + Arrays.toString(sortedArray));
        long t2 = System.currentTimeMillis();
        System.out.println("用时为：" + (t2 - t1));
    }
}
