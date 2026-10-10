package com.example.demo.presentation;

import java.util.Scanner;
import java.util.List;

import com.example.demo.service.CustomerManager;
import com.example.demo.util.Validation;
import com.example.demo.domain.*;
import com.example.demo.service.MenuManager;
import com.example.demo.service.ReservationManager;
import com.example.demo.service.TableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AdminConsole {
    private TableManager tableManager;
    private MenuManager menuManager;
    private ReservationManager resManager;
    private CustomerManager customerManager;
    private Validation v;
    private Scanner sc;

    @Autowired
    public AdminConsole(TableManager tm, MenuManager mm, ReservationManager rm, CustomerManager cm, Validation v, Scanner sc) {
        this.tableManager = tm;
        this.menuManager = mm;
        this.resManager = rm;
        this.customerManager = cm;
        this.v = v;
        this.sc = sc;
    }

    public void start() {
        boolean run = true;
        while (run) {
            System.out.println("\n====Admin panel====");
            System.out.println("1. View tables");
            System.out.println("2. Add new table");
            System.out.println("3. Update table info");
            System.out.println("4. Remove table");
            System.out.println("5. View menu");
            System.out.println("6. Add dish to menu");
            System.out.println("7. Edit menu item");
            System.out.println("8. Remove dish from menu");
            System.out.println("9. View all reservations");
            System.out.println("10. View all customers");
            System.out.println("11. Remove customer");
            System.out.println("12. Exit admin mode");

            int choice = v.getValidInt(sc, "Select an option:");
            switch (choice) {
                case 1:
                    showTables();
                    break;
                case 2:
                    addTable();
                    break;
                case 3:
                    updateTable();
                    break;
                case 4:
                    removeTable();
                    break;
                case 5:
                    showMenu();
                    break;
                case 6:
                    addDish();
                    break;
                case 7:
                    editDish();
                    break;
                case 8:
                    removeDish();
                    break;
                case 9:
                    viewAllReservations();
                    break;
                case 10:
                    viewAllCustomers();
                    break;
                case 11:
                    removeCustomer();
                    break;
                case 12:
                    System.out.println("Goodbye, Admin!");
                    run = false;
                    break;
                default:
                    System.out.println("Wrong input! Try again");
            }
        }
    }

    private void showTables() {
        for (Table t : tableManager.read()) {
            System.out.println("ID: " + t.getId() +
                    " | Name: " + t.getName() +
                    " | Type: " + t.getTypeOfTable() +
                    " | Capacity: " + t.getCapacity());
        }
    }

    private void addTable() {
        System.out.println("Choose table type: 1. Regular, 2. VIP, 3. Student");
        int type = v.getValidInt(sc, "Select an option:");
        if (type < 1 || type > 3) {
            System.out.println("Wrong input!");
            return;
        }
        System.out.println("Enter name of new table:");
        String name = sc.nextLine();
        int capacity = v.getValidInt(sc, "Enter capacity:");

        if (type == 1) tableManager.create(new RegularTable(name, "Regular", capacity));
        else if (type == 2) tableManager.create(new VIPTable(name, "VIP", capacity));
        else tableManager.create(new StudentTable(name, "Student Budget", capacity));

        System.out.println("Table created successfully!");
    }

    private void updateTable() {
        int id = v.getValidInt(sc, "Which table ID to change?");
        Table t = tableManager.readById(id);
        if (t != null) {
            System.out.println("1. Type | 2. Name | 3. Capacity");
            int opt = v.getValidInt(sc, "Select option:");
            String nameToSet = t.getName();
            int capToSet = t.getCapacity();
            String typeToSet = t.getTypeOfTable();

            if (opt == 1) {
                System.out.println("New type: 1. Regular, 2. VIP, 3. Student");
                int typeChoice = v.getValidInt(sc, "Choice:");
                if (typeChoice == 2) typeToSet = "VIP";
                else if (typeChoice == 3) typeToSet = "Student";
                else typeToSet = "Regular";
            } else if (opt == 2) {
                System.out.println("Enter new name:");
                nameToSet = sc.nextLine();
            } else if (opt == 3) {
                capToSet = v.getValidInt(sc, "New capacity:");
            }

            Table updated;
            if (typeToSet.equals("VIP")) updated = new VIPTable(nameToSet, "VIP", capToSet);
            else if (typeToSet.equals("Student")) updated = new StudentTable(nameToSet, "Student Budget", capToSet);
            else updated = new RegularTable(nameToSet, "Regular", capToSet);

            updated.setId(id);
            tableManager.update(id, updated);
            System.out.println("Updated!");
        } else {
            System.out.println("Table not found!");
        }
    }

    private void removeTable() {
        int id = v.getValidInt(sc, "Table ID to delete:");
        try {
            if (tableManager.delete(id)) {
                System.out.println("Deleted!");
            } else {
                System.out.println("Table not found!");
            }
        } catch (Exception e) {
            System.out.println("\nERROR: Cannot delete this table!");
            System.out.println("Reason: This table is currently linked to existing reservations.");
            System.out.println("Please remove the reservations first.");
        }
    }

    private void showMenu() {
        List<Menu> list = menuManager.read();
        list.sort(null);
        if (list.isEmpty()) System.out.println("Menu is empty.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).showMenu());
        }
    }

    private void addDish() {
        System.out.println("Enter dish name:");
        String name = sc.nextLine();
        int grams = v.getValidInt(sc, "Weight (grams):");
        int price = v.getValidInt(sc, "Price:");
        menuManager.create(new Menu(name, grams, price));
        System.out.println("Added successfully!");
    }

    private void editDish() {
        showMenu();
        List<Menu> list = menuManager.read();
        list.sort(null);
        int num = v.getValidInt(sc, "Enter dish number:");
        if (num < 1 || num > list.size()) {
            System.out.println("Wrong number!");
            return;
        }
        Menu m = list.get(num - 1);
        int realDbId = m.getId();
        if (m != null) {
            System.out.println("1. Name | 2. Weight | 3. Price");
            int ch = v.getValidInt(sc, "Choice:");
            String n = m.getName();
            int g = m.getGrams();
            int p = m.getPrice();

            if (ch == 1) {
                System.out.println("New name:");
                n = sc.nextLine();
            } else if (ch == 2) g = v.getValidInt(sc, "New weight:");
            else if (ch == 3) p = v.getValidInt(sc, "New price:");

            menuManager.update(realDbId, new Menu(n, g, p));
            System.out.println("Changed!");
        } else System.out.println("Not found!");
    }

    private void removeDish() {
        showMenu();
        List<Menu> list = menuManager.read();
        list.sort(null);

        int num = v.getValidInt(sc, "Dish number to remove:");
        if (num < 1 || num > list.size()) {
            System.out.println("Wrong number!");
            return;
        }

        int realDbId = list.get(num - 1).getId();

        if (menuManager.delete(realDbId)) {
            System.out.println("Removed!");
        } else {
            System.out.println("Error!");
        }
    }

    private void viewAllReservations() {
        List<Reservation> list = resManager.read();
        if (list.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }
        int index = 1;
        for (Reservation r : list) {
            System.out.println((index++) + ". Customer: " + r.getC().getName() +
                    " | Table: " + r.getTable().getId() +
                    " | Date: " + r.getDate() + " " + r.getTime());
        }
    }
    private void viewAllCustomers() {
        List<Customer> customers = customerManager.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers in the system.");
            return;
        }
        System.out.println("\n=== ALL CUSTOMERS ===");
        for (Customer c : customers) {
            System.out.println("ID: " + c.getId() + " | Name: " + c.getName() + " | Phone: " + c.getPhone());
        }
    }

    private void removeCustomer() {
        viewAllCustomers();

        String phone;
        while (true) {
            System.out.println("Enter Customer phone number to wipe from system:");
            phone = sc.nextLine();

            if (v.valPhone(phone)) {
                break;
            }
        }

        try {
            if (customerManager.deleteCustomerCompletely(phone)) {
                System.out.println("Customer and all their data deleted successfully!");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}