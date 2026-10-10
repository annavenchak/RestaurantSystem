package com.example.demo;

import com.example.demo.domain.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DomainEntitiesTest {

    @Test
    void testMenuAndOrder() {
        Menu menu = new Menu("Pizza", 400, 150);
        assertEquals("Pizza", menu.getName());
        assertEquals(400, menu.getGrams());
        assertEquals(150, menu.getPrice());
        assertTrue(menu.showMenu().contains("Pizza"));

        Order order = new Order(menu, 2);
        assertEquals(300.0, order.getTotalPrice());
        assertEquals(menu, order.getItem());
        assertEquals(2, order.getQuantity());
    }

    @Test
    void testCustomer() {
        Customer customer = new Customer("Oleg", "0501112233");
        Menu menu = new Menu("Coffee", 150, 40);
        Order order = new Order(menu, 1);

        customer.addOrder(order);
        assertEquals(1, customer.getOrderedItems().size());
        assertEquals(40.0, customer.getTotalBill());
        assertEquals("Oleg", customer.getName());
    }

    @Test
    void testTables() {
        RegularTable rt = new RegularTable("T1", "Regular", 4);
        VIPTable vt = new VIPTable("V1", "VIP", 2);
        StudentTable st = new StudentTable("S1", "Student", 6);

        assertEquals(200.0, rt.calculatePrice(2));
        assertEquals(500.0, vt.calculateDeposit());
        assertEquals(0, st.calculatePrice(2));
        assertEquals(50, st.calculatePrice(3)); // 50 * (3-2)
    }

    @Test
    void testRemainingDomainMethods() {
        Customer c = new Customer("Test", "1234567890");
        Table t = new RegularTable("Table", "Regular", 4);

        Reservation res = new Reservation.ReservationBuilder(1, c)
                .table(t)
                .date("01.01.2025")
                .time("12:00")
                .guests(2)
                .hours(2)
                .build();

        assertEquals("01.01.2025", res.getDate());
        assertEquals("12:00", res.getTime());
        assertEquals(2, res.getGuests());
        assertEquals(2, res.getHours());

        res.setDate("02.01.2025");
        res.setTime("13:00");
        res.setGuests(4);
        res.setHours(3);
        res.setId(10);
        assertEquals(10, res.getId());

        Menu m = new Menu();
        m.setName("Water");
        m.setGrams(500);
        m.setPrice(20);
        m.setId(5);
        assertEquals(5, m.getId());

        VIPTable vip = new VIPTable(1, "VIP", "VIP", 5);
        assertEquals(500.0, vip.calculateDeposit());

        StudentTable student = new StudentTable("Stud", "Student", 4);
        student.setCapacity(10);
        assertEquals(10, student.getCapacity());
    }
}