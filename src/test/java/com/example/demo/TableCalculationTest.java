package com.example.demo;

import com.example.demo.domain.StudentTable;
import com.example.demo.domain.VIPTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableCalculationTest {
    @Test
    void testStudentTablePrice() {
        StudentTable st = new StudentTable("Zone", "Student", 2);
        assertEquals(0, st.calculatePrice(2));
        assertEquals(50, st.calculatePrice(3));
    }

    @Test
    void testVIPTablePriceAndDeposit() {
        VIPTable vip = new VIPTable("VIP 1", "VIP", 4);
        assertEquals(500, vip.calculateDeposit());
        assertEquals(900, vip.calculatePrice(3));
    }
}
