package com.example.demo.util;

import java.util.Iterator;

public class DoublyLinkedList <T extends Comparable<T>> implements Iterable<T> {
    private class Node {
        T data;
        Node prev;
        Node next;
        Node(T data) { this.data = data; }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.data;
    }

    public void set(int index, T data) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        current.data = data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;

        if (current.prev != null) current.prev.next = current.next;
        else head = current.next;

        if (current.next != null) current.next.prev = current.prev;
        else tail = current.prev;

        size--;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void sort() {
        if (size > 1) {
            boolean wasChanged;
            do {
                Node current = head;
                wasChanged = false;
                while (current.next != null) {
                    if ((current.data).compareTo(current.next.data) > 0) {
                        T temp = current.data;
                        current.data = current.next.data;
                        current.next.data = temp;
                        wasChanged = true;
                    }
                    current = current.next;
                }
            } while (wasChanged);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;
            @Override
            public boolean hasNext() { return current != null; }
            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}