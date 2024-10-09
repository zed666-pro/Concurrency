package StructuredConcurency.ScopedValue;

/**
 * @Author shengaojie
 * @Date 2023/4/14 15:37
 * @ClassName: withScopedValue
 * @Description: TODO
 * @Version 1.0
 */
public class WithNothing {
    public static void main(String[] args) {
       new Student().travel();
    }
}

class Student {

    public void travel(){
        Food food = new Food();
        Transport transport = new Transport();
        transport.takeTrain(food);
    }
}

class Transport{
    public void takeTrain(Food food){
        takebus(food);
    }

    public void takebus(Food food){
        byWalk(food);
    }

    public void byWalk(Food food){
        boolean exsit = food.isExsit();
        System.out.println(exsit);
    }
}

class Food{

    public boolean isExsit(){
        return false;
    }

}

