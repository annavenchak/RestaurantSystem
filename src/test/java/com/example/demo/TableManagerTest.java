package com.example.demo;

import com.example.demo.domain.RegularTable;
import com.example.demo.domain.Table;
import com.example.demo.repository.TableRepository;
import com.example.demo.service.TableManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableManagerTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableManager tableManager;

    @Test
    void testGetCapacityOfTable_Positive() {
        Table table = new RegularTable("Table 1", "Regular", 4);
        when(tableRepository.findById(1)).thenReturn(Optional.of(table));

        int capacity = tableManager.getCapacityOfTable(1);

        assertEquals(4, capacity);
    }

    @Test
    void testGetCapacityOfTable_NotFound() {
        when(tableRepository.findById(99)).thenReturn(Optional.empty());

        int capacity = tableManager.getCapacityOfTable(99);

        assertEquals(-1, capacity);
    }
}