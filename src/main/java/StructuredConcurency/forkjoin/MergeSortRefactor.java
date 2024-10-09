package StructuredConcurency.forkjoin;

import java.util.concurrent.ExecutionException;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * @Author shengaojie @Date 2023/5/31 10:59 @ClassName: MergeSort @Description: TODO @Version 1.0
 */
public class MergeSortRefactor {
  public static void merge(int[] arr, int left, int mid, int right) {
    int n1 = mid - left + 1;
    int n2 = right - mid;

    int[] leftArr = new int[n1];
    int[] rightArr = new int[n2];

    for (int i = 0; i < n1; ++i) leftArr[i] = arr[left + i];
    for (int j = 0; j < n2; ++j) rightArr[j] = arr[mid + 1 + j];

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

  public static void mergeSort(int[] arr, int left, int right)
      throws InterruptedException, ExecutionException {

    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      scope.fork(
          () -> {
            if (left < right) {
              int mid = (left + right) / 2;
              mergeSort(arr, left, mid);
              mergeSort(arr, mid + 1, right);
              merge(arr, left, mid, right);
            }
            return null;
          });
      long t1 = System.currentTimeMillis();
      scope.join();
      long t2 = System.currentTimeMillis();

      scope.throwIfFailed();
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    long t1 = System.currentTimeMillis();
    int[] arr = MergeSort.getArray();
    mergeSort(arr, 0, arr.length - 1);
    long t2 = System.currentTimeMillis();
    System.out.println();
    System.out.println("所有时间为:" + (t2 - t1));
  }
}
