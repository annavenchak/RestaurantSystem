package com.example.demo;

import com.example.demo.domain.*;
import com.example.demo.service.*;
import com.example.demo.util.DoublyLinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalTargetTest {

    @Mock private ReservationManager reservationManager;
    @Mock private TableManager tableManager;
    @InjectMocks private CustomerReservationManager customerReservationManager;

    @Test
    void testCustomerReservationManager_Logic() {
        Customer c1 = new Customer("Oleg", "111");
        c1.setId(1);
        Customer c2 = new Customer("Anna", "222");
        c2.setId(2);

        Reservation r1 = new Reservation.ReservationBuilder(1, c1).build();
        Reservation r2 = new Reservation.ReservationBuilder(2, c2).build();

        when(reservationManager.read()).thenReturn(List.of(r1, r2));

        DoublyLinkedList<Reservation> result = customerReservationManager.read(c1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());

        customerReservationManager.create(r1);
        verify(reservationManager).create(r1);

        customerReservationManager.delete(1);
        verify(reservationManager).delete(1);
    }

    @Test
    void testMenuManager_CreateOrder_Logic() {
        Menu item = new Menu("Coffee", 100, 50);
    }

    @Test
    void testReservationPriceCalculation() {
        Table vip = new VIPTable("VIP", "VIP", 4);
        Customer c = new Customer("User", "000");

        Reservation res = new Reservation.ReservationBuilder(1, c)
                .table(vip)
                .hours(3)
                .build();
        assertEquals(1400.0, res.getTotalPrice());
    }
}