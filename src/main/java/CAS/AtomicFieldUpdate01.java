package CAS;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicFieldUpdate01 {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<Cat> update =
                     AtomicIntegerFieldUpdater.newUpdater(Cat.class, "age");
        Cat lihua = new Cat("lihua", 10);
        int i = update.addAndGet(lihua, 20);
        System.out.println(i);


    }
}


  class Cat{
    public String name;
    //未被volatile关键字修饰的变量无法被更新
    public volatile int age;


    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}