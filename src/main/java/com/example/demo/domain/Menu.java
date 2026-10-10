package com.example.demo.domain;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
@Entity
@jakarta.persistence.Table(name = "dishes")
public class Menu implements Comparable<Menu>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

        private String name;
        private int grams;
        private int price;
        public Menu() {}
        @Autowired
        public Menu(String name, int grams, int price) {
            setName(name);
            setGrams(grams);
            setPrice(price);
        }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String showMenu() {
        return name + " - " + grams + "g - $" + price;
    }

    @Override
    public int compareTo(Menu other) {
        return Integer.compare(this.price, other.price);
    }
}
