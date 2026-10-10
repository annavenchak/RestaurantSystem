package com.example.demo.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "customers")
public class Customer implements Comparable<Customer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Order> orderedItems = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() { return id; }

    public String getName(){ return name; }

    public String getPhone(){ return phone; }

    public void addOrder(Order item){
        orderedItems.add(item);
    }

    public List<Order> getOrderedItems() {
        return orderedItems;
    }

    public double getTotalBill(){
        double total = 0;
        for(Order o : orderedItems){
            total += o.getTotalPrice();
        }
        return total;
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.compareTo(o.getName());
    }
}