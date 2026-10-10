package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "orders")
public class Order implements Comparable<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu item;

    @Column(nullable = false)
    private int quantity;

    public Order() {}

    public Order(Menu item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Menu getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }

    @Override
    public int compareTo(Order o) {
        return Double.compare(this.getTotalPrice(), o.getTotalPrice());
    }
}