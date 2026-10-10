package com.example.demo.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VIP")
public class VIPTable extends Table {
    public VIPTable() {}

    public VIPTable(int id, String name, String typeOfTable, int capacity) {
        super(id, name, typeOfTable, capacity);
    }

    public VIPTable(String name, String typeOfTable, int capacity) {
        super(name, typeOfTable, capacity);
    }

    @Override
    public double calculatePrice(int hours) {
        return 300 * hours;
    }

    @Override
    public double calculateDeposit() {
        return 500;
    }

    @Override
    public int compareTo(Table o) {
        return 0;
    }
}