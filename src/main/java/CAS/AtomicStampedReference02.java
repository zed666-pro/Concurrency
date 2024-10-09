package CAS;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReference02 {
    static AtomicStampedReference<Dog> asr =
            new AtomicStampedReference<>(new Dog(10, "xiaobai"), 1);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    Dog dog1 = asr.getReference();
                    Dog dog2 = new Dog(dog1.getAge()+1, dog1.getName());
                    if(asr.compareAndSet(dog1,dog2,asr.getStamp(),asr.getStamp()+1))
                        System.out.println(dog2);

                }
            }.start();
        }

    }
}

class Dog{
    private int age;
    private String name;

    public Dog(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
