package com.example.demo;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Menu;
import com.example.demo.domain.RegularTable;
import com.example.demo.domain.Table;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.service.CustomerManager;
import com.example.demo.service.MenuManager;
import com.example.demo.service.TableManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceFullTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private MenuRepository menuRepository;
    @Mock private TableRepository tableRepository;

    @InjectMocks private CustomerManager customerManager;
    @InjectMocks private MenuManager menuManager;
    @InjectMocks private TableManager tableManager;

    @Test
    void testCustomerManagerComplete() {
        Customer customer = new Customer("Ivan", "0991112233");

        customerManager.create(customer);
        verify(customerRepository, times(1)).save(customer);

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        assertEquals(1, customerManager.read().size());

        when(customerRepository.existsById(1)).thenReturn(true);
        assertTrue(customerManager.update(1, customer));

        when(customerRepository.findByPhone("0991112233")).thenReturn(customer);
        Customer found = customerManager.findOrCreate("Ivan", "0991112233");
        assertNotNull(found);
    }

    @Test
    void testMenuManagerComplete() {
        Menu dish = new Menu("Borsch", 300, 100);

        when(menuRepository.findById(1)).thenReturn(Optional.of(dish));
        assertEquals("Borsch", menuManager.readById(1).getName());

        when(menuRepository.existsById(1)).thenReturn(true);
        assertTrue(menuManager.update(1, dish));
        assertTrue(menuManager.delete(1));
    }

    @Test
    void testTableManagerComplete() {
        Table table = new RegularTable("Window Table", "Regular", 4);

        tableManager.create(table);
        verify(tableRepository, times(1)).save(table);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        assertEquals(4, tableManager.getCapacityOfTable(1));

        when(tableRepository.count()).thenReturn(5L);
        assertEquals(5, tableManager.getTablesSize());
    }
}