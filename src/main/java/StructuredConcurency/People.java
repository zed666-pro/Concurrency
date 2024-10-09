package StructuredConcurency;

/**
 * @description: null
 * @author: shengaojie
 * @create: 2023-11-06
 **/

public class People {
    String name;

    public People(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "StructuredConcurency.People{" +
                "name='" + name + '\'' +
                '}';
    }
}
