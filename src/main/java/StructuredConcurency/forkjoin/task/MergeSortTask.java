package StructuredConcurency.forkjoin.task;

/**
 * @Author shengaojie @Date 2023/6/1 16:57 @ClassName: MergeSortTask @Description: TODO @Version 1.0
 */
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortTask extends RecursiveTask<int[]> {
  private int[] array;
  private int start;
  private int end;

  public MergeSortTask(int[] array, int start, int end) {
    this.array = array;
    this.start = start;
    this.end = end;
  }

  @Override
  protected int[] compute() {
    if (end - start <= 1) {
      // Base case: if the range is small, use traditional merge sort
      return Arrays.copyOfRange(array, start, end);
    } else {
      // Divide the range into two halves
      int mid = (start + end) / 2;
      MergeSortTask leftTask = new MergeSortTask(array, start, mid);
      MergeSortTask rightTask = new MergeSortTask(array, mid, end);
      // Fork the left task to a separate thread
      leftTask.fork();
      rightTask.fork();

      // Compute the right task in the current thread
      int[] rightResult = rightTask.join();
      // Wait for the left task to complete and get its result
      int[] leftResult = leftTask.join();
      // Merge the results of the two subtasks
      return merge(leftResult, rightResult);
    }
  }

  private int[] merge(int[] left, int[] right) {
    int[] merged = new int[left.length + right.length];
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
      if (left[i] <= right[j]) {
        merged[k++] = left[i++];
      } else {
        merged[k++] = right[j++];
      }
    }

    while (i < left.length) {
      merged[k++] = left[i++];
    }

    while (j < right.length) {
      merged[k++] = right[j++];
    }

    return merged;
  }
}
