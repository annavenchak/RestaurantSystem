package com.example.demo.service;
import java.util.List;
public interface CRUDOperations<T extends Comparable<T>> {

    void create(T item);

    List<T> read();

    boolean update(int id, T newItem);

    boolean delete(int id);
}