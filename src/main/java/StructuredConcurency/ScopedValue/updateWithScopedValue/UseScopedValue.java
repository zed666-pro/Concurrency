package StructuredConcurency.ScopedValue.updateWithScopedValue;


import jdk.incubator.concurrent.ScopedValue;

import static StructuredConcurency.ScopedValue.updateWithScopedValue.UseScopedValue.food;

/**
 * @Author shengaojie
 * @Date 2023/4/14 16:02
 * @ClassName: UseScopedValue
 * @Description: TODO
 * @Version 1.0
 */
public class UseScopedValue {
    public static final ScopedValue<Food> food = ScopedValue.newInstance();
    public static void main(String[] args) {
        new Student().travel();
    }
}

class Student {
    public void travel(){

        Transport transport = new Transport();
        ScopedValue.where(food,new Food()).run(() -> {
            transport.takeTrain();
        });

    }
}

class Transport{
    public void takeTrain(){
        takebus();
    }

    public void takebus( ){
        byWalk();
    }

    public void byWalk(){
        Food food1 = food.get();
        boolean exsit = food1.isExsit();
        System.out.println(exsit);
    }
}

class Food{
    public boolean isExsit(){
        return false;
    }

}
