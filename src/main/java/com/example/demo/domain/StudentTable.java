package com.example.demo.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDENT")
public class StudentTable extends Table {

    public StudentTable() {}

    public StudentTable(String name, String typeOfTable, int capacity) {
        super(name, typeOfTable, capacity);
    }

    @Override
    public double calculatePrice(int hours) {
        if (hours <= 2) {
            return 0;
        } else {
            return 50 * (hours - 2);
        }
    }

    @Override
    public double calculateDeposit() {
        return 0;
    }

    @Override
    public int compareTo(Table o) {
        return 0;
    }
}