package com.example.demo;

import com.example.demo.domain.Menu;
import com.example.demo.domain.Table;
import com.example.demo.domain.RegularTable;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.service.MenuManager;
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
class ManagersExtraTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private MenuManager menuManager;
    @InjectMocks
    private TableManager tableManager;

    @Test
    void testMenuManagerCRUD() {
        Menu menu = new Menu("Tea", 200, 30);
        when(menuRepository.existsById(1)).thenReturn(true);

        assertTrue(menuManager.update(1, menu));
        assertTrue(menuManager.delete(1));

        when(menuRepository.findById(1)).thenReturn(Optional.of(menu));
        assertNotNull(menuManager.readById(1));
    }

    @Test
    void testTableManagerCRUD() {
        Table table = new RegularTable("Table X", "Regular", 4);
        when(tableRepository.existsById(10)).thenReturn(true);

        assertTrue(tableManager.update(10, table));
        assertTrue(tableManager.delete(10));
        assertEquals(0, tableManager.getTablesSize());
    }
}