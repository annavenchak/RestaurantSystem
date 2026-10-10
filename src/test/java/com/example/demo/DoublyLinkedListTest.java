package com.example.demo;

import com.example.demo.util.DoublyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

   private DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test
    void testAddAndSize_IsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(10);
        list.add(20);

        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    void testGet_SuccessAndOutOfBounds() {
        list.add(10);
        list.add(20);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testSet() {
        list.add(10);
        list.add(20);

        list.set(0, 99);

        assertEquals(99, list.get(0));
        assertEquals(2, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(5, 100));
    }

    @Test
    void testRemove() {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));

        list.remove(0);
        assertEquals(30, list.get(0));

        list.remove(0);
        assertTrue(list.isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    void testSort() {
        list.add(30);
        list.add(10);
        list.add(20);
        list.add(5);

        list.sort();

        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
    }

    @Test
    void testIterator() {
        list.add(100);
        list.add(200);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(100, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(200, iterator.next());
        assertFalse(iterator.hasNext());
    }
}