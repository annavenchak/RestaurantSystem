package com.example.demo.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REGULAR")
public class RegularTable extends Table {

    public RegularTable() {}

    public RegularTable(int id, String name, String typeOfTable, int capacity){
        super(id, name, typeOfTable, capacity);
    }

    public RegularTable(String name, String typeOfTable, int capacity){
        super(name, typeOfTable, capacity);
    }

    @Override
    public double calculatePrice(int hours) { return 100 * hours; }

    @Override
    public double calculateDeposit() { return 0; }

    @Override
    public int compareTo(Table o) { return 0; }
}