package StructuredConcurency.StructureDemo;


import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @Author shengaojie @Date 2023/10/11 20:23 @ClassName: TryTest @Description: TODO @Version 1.0
 */
public class TryTest {
    public static void main(String[] args) {
        try (Connection connection = null;) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() {
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(99);
        arrayList.add("reflect");
        System.out.println(arrayList);
    }
}
