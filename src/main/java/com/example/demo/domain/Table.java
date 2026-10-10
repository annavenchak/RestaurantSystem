package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "restaurant_tables")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "table_category", discriminatorType = DiscriminatorType.STRING)
public abstract class Table implements Comparable<Table> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false)
    protected String name;

    @Column(name = "type_of_table")
    protected String typeOfTable;

    protected int capacity;

    public Table() {}

    public Table(int id, String name, String typeOfTable, int capacity){
        this.id = id;
        this.name = name;
        this.typeOfTable = typeOfTable;
        this.capacity = capacity;
    }

    public Table(String name, String typeOfTable, int capacity){
        this.name = name;
        this.typeOfTable = typeOfTable;
        this.capacity = capacity;
    }

    public abstract double calculatePrice(int hours);
    public abstract double calculateDeposit();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getTypeOfTable() { return typeOfTable; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}