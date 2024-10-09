package CAS;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArray01 {
    public static void main(String[] args) {
        int [] array = {1,2,3,4};

        AtomicIntegerArray array1 = new AtomicIntegerArray(array);
        System.out.println(array1.addAndGet(1, 10));
        for (int i = 0; i < array1.length(); i++) {
            System.out.println(array1.get(i));
        }

    }
}
