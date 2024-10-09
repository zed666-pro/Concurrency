package CAS;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReference01 {
    public static void main(String[] args) {
        AtomicStampedReference<String> hello =
                    new AtomicStampedReference<>("hello", 1);
        String reference = hello.getReference();
        System.out.println(reference);

        hello.compareAndSet("hello","world",1,2);
        System.out.println(hello.getReference());
        System.out.println(hello.getStamp());
        int [] array = new int[1];
        String s = hello.get(array);
        System.out.println(s);
        System.out.println(array[0]);
    }
}


