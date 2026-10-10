package com.example.demo;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Reservation;
import com.example.demo.service.BillManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillManagerTest {

    @InjectMocks
    private BillManager billManager;

    @Test
    void testShowTotalBill_CustomerOnly() {
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Anna");
        when(mockCustomer.getTotalBill()).thenReturn(240.0);

        billManager.showTotalBill(mockCustomer);

        verify(mockCustomer, times(1)).getTotalBill();
        verify(mockCustomer, times(1)).getName();
    }

    @Test
    void testShowTotalBill_WithReservation() {
        Customer mockCustomer = mock(Customer.class);
        Reservation mockRes = mock(Reservation.class);

        when(mockCustomer.getName()).thenReturn("Anna");
        when(mockCustomer.getTotalBill()).thenReturn(240.0);
        when(mockRes.getTotalPrice()).thenReturn(50.0);

        billManager.showTotalBill(mockCustomer, mockRes);

        verify(mockRes, times(1)).getTotalPrice();
        verify(mockCustomer, times(1)).getTotalBill();
    }
}