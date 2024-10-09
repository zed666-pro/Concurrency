package StructuredConcurency.ScopedValue.updateWithThreadLocal;

import static StructuredConcurency.ScopedValue.updateWithThreadLocal.UseThreadLocal.food;

/**
 * @Author shengaojie
 * @Date 2023/4/14 15:56
 * @ClassName: UseThreadLocal
 * @Description: TODO
 * @Version 1.0
 */
public class UseThreadLocal {
    public static ThreadLocal<Food> food = new ThreadLocal<>();
    public static void main(String[] args) {
        new Student().travel();
        food.remove();

    }
}

class Student {

    public void travel(){
        food.set(new Food());
       Transport transport = new Transport();
        transport.takeTrain();
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

