package com.example.demo.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "reservations")
public class Reservation implements Comparable<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer c;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private Table table;

    @Column(name = "res_date", nullable = false)
    private String date;

    @Column(name = "res_time", nullable = false)
    private String time;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false)
    private int hours;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private List<Order> foodOrders = new ArrayList<>();

    public Reservation() {}

    private Reservation(ReservationBuilder builder) {
        this.id = builder.id;
        this.c = builder.c;
        this.table = builder.table;
        this.date = builder.date;
        this.time = builder.time;
        this.guests = builder.guests;
        this.hours = builder.hours;
    }
    public static class ReservationBuilder {
        private int id;
        private Customer c;
        private Table table;
        private String date;
        private String time;
        private int guests;
        private int hours;
        public ReservationBuilder(int id, Customer c) {
            this.id = id;
            this.c = c;
        }
        public ReservationBuilder table(Table table) { this.table = table; return this; }
        public ReservationBuilder date(String date) { this.date = date; return this; }
        public ReservationBuilder time(String time) { this.time = time; return this; }
        public ReservationBuilder guests(int guests) { this.guests = guests; return this; }
        public ReservationBuilder hours(int hours) { this.hours = hours; return this; }
        public Reservation build() {
            return new Reservation(this);
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getC() { return c; }
    public void setC(Customer c) { this.c = c; }

    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public List<Order> getFoodOrders() { return foodOrders; }

    public void addFoodOrder(Order order) {
        foodOrders.add(order);
    }

    public double getTotalPrice() {
        return table.calculateDeposit() + table.calculatePrice(hours);
    }

    @Override
    public int compareTo(Reservation o) {
        return Integer.compare(this.id, o.id);
    }
}