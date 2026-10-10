package com.example.demo;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerManagerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerManager customerManager;
    @Test
    void testDeleteCustomerCompletely_Success() {
        String phone = "0991234567";
        Customer customer = new Customer("Ivan", phone);

        when(customerRepository.findByPhone(phone)).thenReturn(customer);

        boolean result = customerManager.deleteCustomerCompletely(phone);

        assertTrue(result);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void testDeleteCustomerCompletely_NotFound() {
        String phone = "0000000000";

        when(customerRepository.findByPhone(phone)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            customerManager.deleteCustomerCompletely(phone);
        });

        verify(customerRepository, never()).delete(any());
    }
}