package StructuredConcurency.forkjoin;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author shengaojie
 * @Date 2023/5/31 14:11
 * @ClassName: MergeSort
 * @Description: TODO
 * @Version 1.0
 */
public class MergeSort {
    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; ++i)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        int[] arr = getArray();

        mergeSort(arr, 0, arr.length - 1);

        System.out.println("\n排序后的数组:");
        System.out.println(Arrays.toString(arr));
        long t2 = System.currentTimeMillis();
        System.out.println();
        System.out.println("所有时间为：" + (t2 - t1));
    }

    public static int [] getArray(){
        Random random = new Random();
        int [] arr = new int[10_000_000];
        for(int i = 0;i < 10_000_000;i++){
            int temp = random.nextInt(10_000_0000);
            arr[i] = temp;
        }

        return arr;

    }
}

