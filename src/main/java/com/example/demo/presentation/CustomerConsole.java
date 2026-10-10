package com.example.demo.presentation;

import java.util.List;
import java.util.Scanner;

import com.example.demo.domain.*;
import com.example.demo.util.DoublyLinkedList;
import com.example.demo.util.Validation;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CustomerConsole {
    private CustomerManager customerManager;
    private TableManager tableManager;
    private ReservationManager resManager;
    private CustomerReservationManager crm;
    private MenuManager menuManager;
    private BillManager billManager;
    private Validation v;
    private Scanner sc;
    @Autowired
    public CustomerConsole(CustomerManager cm, TableManager tm, ReservationManager rm,
                           CustomerReservationManager crm, MenuManager mm,
                           BillManager rs, Validation v, Scanner sc) {
        this.customerManager = cm;
        this.tableManager = tm;
        this.resManager = rm;
        this.crm = crm;
        this.menuManager = mm;
        this.billManager = rs;
        this.v = v;
        this.sc = sc;
    }

    public void start(Customer currentCustomer) {
        boolean run = true;
        while (run) {
            System.out.println("\n====Customer panel====");
            System.out.println("1. View tables");
            System.out.println("2. Book a table");
            System.out.println("3. View reservations");
            System.out.println("4. Edit reservations");
            System.out.println("5. Cancel reservation");
            System.out.println("6. Order food");
            System.out.println("7. View food bill");
            System.out.println("8. View total bill");
            System.out.println("9. Exit customer mode");

            int choice = v.getValidInt(sc, "Select an option:");
            switch (choice) {
                case 1: showTables(); break;
                case 2: makeBooking(currentCustomer); break;
                case 3: viewMyReservations(currentCustomer); break;
                case 4: editReservation(currentCustomer); break;
                case 5: cancelReservation(currentCustomer); break;
                case 6: orderFood(currentCustomer); break;
                case 7: billManager.showTotalBill(currentCustomer); break;
                case 8: showTotalBillWithRes(currentCustomer); break;
                case 9:
                    System.out.println("Goodbye, " + currentCustomer.getName() + "!");
                    run = false;
                    break;
                default:
                    System.out.println("Wrong input! Try again");
            }
        }
    }

    private void showTables() {
        for(Table t : tableManager.read()){
            System.out.println("ID: " + t.getId() +
                    " | Name: " + t.getName() +
                    " | Type: " + t.getTypeOfTable() +
                    " | Capacity: " + t.getCapacity());
        }
    }

    private void makeBooking(Customer currentCustomer) {
        int id;
        Table t = null;
        do {
            id = v.getValidInt(sc, "Enter table ID:");
            t = tableManager.readById(id); // Звертаємось до БД

            if (t == null) {
                System.out.println("Error: Table ID not found!");
            }
        } while (t == null);

        String date;
        do {
            System.out.println("Enter date (dd.mm.yyyy):");
            date = sc.nextLine();
        } while (!v.valDate(date));

        String time;
        do {
            System.out.println("Enter time (hh:mm):");
            time = sc.nextLine();
        } while (!v.valTime(time));

        int guests;
        do {
            guests = v.getValidInt(sc, "Enter number of guests:");
            if (guests < 1 || guests > tableManager.getCapacityOfTable(id)) {
                System.out.println("Error: Guest count exceeds table capacity!");
            }
        } while (guests < 1 || guests > tableManager.getCapacityOfTable(id));

        int hours;
        do {
            hours = v.getValidInt(sc, "Enter duration (hours):");
        } while (hours < 1);

        Reservation newRes = new Reservation.ReservationBuilder(resManager.getNextId(), currentCustomer)
                .table(t)
                .date(date)
                .time(time)
                .guests(guests)
                .hours(hours)
                .build();

        resManager.create(newRes);
        System.out.println("Booked! Total price: $ " + newRes.getTotalPrice());
    }

    private void viewMyReservations(Customer currentCustomer) {
        System.out.println("=== YOUR RESERVATIONS, " + currentCustomer.getName() + " ===");
        DoublyLinkedList<Reservation> myRes = crm.read(currentCustomer);
        if (myRes.isEmpty()) {
            System.out.println("You have no active bookings.");
            return;
        }
        int index = 1;
        for (Reservation res : myRes) {
            System.out.println(index++ + ". Table: " + res.getTable().getId() +
                    " | Date: " + res.getDate() + " | Time: " + res.getTime() +
                    " | Guests: " + res.getGuests() + " | Hours: " + res.getHours());
        }
    }

    private void editReservation(Customer currentCustomer) {
        DoublyLinkedList<Reservation> myRes = crm.read(currentCustomer);

        if (myRes.isEmpty()) {
            System.out.println("You have no reservations to edit.");
            return;
        }
        System.out.println("=== YOUR RESERVATIONS ===");
        for (Reservation r : myRes) {
            System.out.println("ID: " + r.getId() + " | Date: " + r.getDate() + " | Time: " + r.getTime());
        }
        int idToEdit = v.getValidInt(sc, "Enter reservation ID to change:");

        Reservation reservationToEdit = null;
        for (Reservation r : myRes) {
            if (r.getId() == idToEdit) {
                reservationToEdit = r;
                break;
            }
        }
        if (reservationToEdit != null) {
            System.out.println("What do you want to change?");
            System.out.println("1. Table | 2. Date | 3. Time | 4. Guests | 5. Duration");

            int ch = v.getValidInt(sc, " ");
            switch (ch) {
                case 1:
                    int tId = v.getValidInt(sc, "Enter new table ID:");
                    Table table = tableManager.readById(tId);
                    if (table != null && reservationToEdit.getGuests() <= table.getCapacity()) {
                        reservationToEdit.setTable(table);
                        System.out.println("Table changed successfully!");
                    } else {
                        System.out.println("Error: Table too small or not found");
                    }
                    break;
                case 2:
                    String d;
                    do {
                        System.out.println("New date (dd.mm.yyyy):");
                        d = sc.nextLine();
                    } while (!v.valDate(d));
                    reservationToEdit.setDate(d);
                    System.out.println("Date changed successfully!");
                    break;
                case 3:
                    String tm;
                    do {
                        System.out.println("New time (hh:mm):");
                        tm = sc.nextLine();
                    } while (!v.valTime(tm));
                    reservationToEdit.setTime(tm);
                    System.out.println("Time changed successfully!");
                    break;
                case 4:
                    int g = v.getValidInt(sc, "New guests:");
                    if (g >= 1 && g <= reservationToEdit.getTable().getCapacity()) {
                        reservationToEdit.setGuests(g);
                        System.out.println("Guests updated!");
                    } else {
                        System.out.println("Error: Too many guests for current table!");
                    }
                    break;
                case 5:
                    int h = v.getValidInt(sc, "New duration (hours):");
                    if (h >= 1) {
                        reservationToEdit.setHours(h);
                        System.out.println("Duration updated!");
                    } else {
                        System.out.println("Error: Invalid hours!");
                    }
                    break;
                default:
                    System.out.println("Wrong input!");
            }
            resManager.update(reservationToEdit.getId(), reservationToEdit);
        } else {
            System.out.println("SECURITY ERROR: Reservation not found or belongs to another customer!");
        }
    }

    private void cancelReservation(Customer currentCustomer) {
        DoublyLinkedList<Reservation> myRes = crm.read(currentCustomer);

        if (myRes.isEmpty()) {
            System.out.println("You have no reservations to cancel.");
            return;
        }
        System.out.println("=== YOUR RESERVATIONS ===");
        for (Reservation r : myRes) {
            System.out.println("ID: " + r.getId() + " | Date: " + r.getDate() + " | Time: " + r.getTime());
        }
        int idToDelete = v.getValidInt(sc, "Enter ID to delete:");
        boolean isMyReservation = false;
        for (Reservation r : myRes) {
            if (r.getId() == idToDelete) {
                isMyReservation = true;
                break;
            }
        }
        if (isMyReservation) {
            if (resManager.delete(idToDelete)) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Error deleting reservation from system.");
            }
        } else {
            System.out.println("SECURITY ERROR: You can only cancel YOUR OWN reservations!");
        }
    }

    private void orderFood(Customer currentCustomer) {
        DoublyLinkedList<Reservation> myRes = crm.read(currentCustomer);
        if (myRes.isEmpty()) {
            System.out.println("Book a table first!");
            return;
        }
        for (Reservation res : myRes) System.out.println("ID: " + res.getId() + " | Date: " + res.getDate());
        int id = v.getValidInt(sc, "Choose reservation ID:");
        Reservation selected = resManager.readById(id);

        if (selected != null) {
            List<Menu> menus = menuManager.read();
            menus.sort(null);

            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i+1) + ". " + menus.get(i).showMenu());
            }
            int mIdx = v.getValidInt(sc, "Dish number:");
            if (mIdx < 1 || mIdx > menus.size()) {
                System.out.println("Wrong number!");
                return;
            }

            int port = v.getValidInt(sc, "Quantity:");

            Menu item = menus.get(mIdx - 1);
            Order o = new Order(item, port);
            currentCustomer.addOrder(o);
            System.out.println("Added: " + o.getItem().getName());

            resManager.update(selected.getId(), selected);
            customerManager.update(currentCustomer.getId(), currentCustomer);
        }
    }

    private void showTotalBillWithRes(Customer currentCustomer) {
        DoublyLinkedList<Reservation> myRes = crm.read(currentCustomer);
        if (myRes.isEmpty()) {
            System.out.println("No reservations yet");
        } else {
            for (Reservation res : myRes) {
                System.out.println("ID: " + res.getId() + " | Date: " + res.getDate());
            }
            int id = v.getValidInt(sc, "Choose reservation ID for the bill:");
            Reservation selected = resManager.readById(id);

            if (selected != null && selected.getC().getId() == currentCustomer.getId()) {
                billManager.showTotalBill(currentCustomer, selected);
            } else {
                System.out.println("Reservation not found or belongs to someone else!");
            }
        }
    }
}