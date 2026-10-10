package com.example.demo;

import com.example.demo.domain.Menu;
import com.example.demo.service.MenuManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "run.menu=false")
class MenuManagerTest {

    @Autowired
    private MenuManager menuManager;

    // позитивний сценарій
    @Test
    void testCreateDish_Positive() {
        int initialSize = menuManager.read().size();

        Menu newDish = new Menu("Test Cake", 150, 50);
        menuManager.create(newDish);

        assertEquals(initialSize + 1, menuManager.read().size());
    }

    // негативний сценарій
    @Test
    void testDeleteDish_NonExistentId_Negative() {
        boolean result = menuManager.delete(9999);
        assertFalse(result);
    }
}