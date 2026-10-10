package com.example.demo;

import com.example.demo.util.Validation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    private final Validation v = new Validation();

    // позитивні сценарії
    @Test
    void valPhone_CorrectPhone_ReturnsTrue() {
        assertTrue(v.valPhone("0987654321"));
    }

    @Test
    void valDate_CorrectDate_ReturnsTrue() {
        assertTrue(v.valDate("15.05.2025"));
    }

    // негативні сценарії
    @Test
    void valPhone_PhoneWithLetters_ReturnsFalse() {
        assertFalse(v.valPhone("09876abcde"));
    }

    @Test
    void valPhone_ShortPhone_ReturnsFalse() {
        assertFalse(v.valPhone("12345"));
    }

    @Test
    void valDate_WrongFormat_ReturnsFalse() {
        assertFalse(v.valDate("15/05/2025"));
        assertFalse(v.valDate("99.99.2025"));
    }
}