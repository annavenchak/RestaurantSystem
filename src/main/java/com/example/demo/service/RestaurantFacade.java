package com.example.demo.service;

import com.example.demo.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RestaurantFacade {

    private final CustomerManager customerManager;
    private final TableManager tableManager;
    private final ReservationManager reservationManager;
    private final MenuManager menuManager;
    private final BillManager billManager;

    public RestaurantFacade(CustomerManager customerManager,
                            TableManager tableManager,
                            ReservationManager reservationManager,
                            MenuManager menuManager,
                            BillManager billManager) {
        this.customerManager = customerManager;
        this.tableManager = tableManager;
        this.reservationManager = reservationManager;
        this.menuManager = menuManager;
        this.billManager = billManager;
    }

    public void bookTableAndOrder(String customerName, String phone, int tableId, int menuId, int quantity, String date, String time, int guests) {
        System.out.println("--- Починаємо обробку комплексного замовлення ---");

        Menu item = menuManager.readById(menuId);
        Table table = tableManager.readById(tableId);
        if (table == null) {
            System.out.println("Помилка: Столик з ID " + tableId + " не знайдено!");
            return;
        }
        Customer customer = new Customer(customerName, phone);
        if (item != null) {
            Order order = new Order(item, quantity);
            customer.addOrder(order);
            System.out.println("Замовлення додано: " + item.getName() + ", кількість: " + quantity);
        }
        customerManager.create(customer);

        if (table != null) {
            Reservation reservation = new Reservation.ReservationBuilder(0, customer)
                    .date(date)
                    .time(time)
                    .guests(guests)
                    .table(table)
                    .build();

            reservationManager.create(reservation);
            System.out.println("Стіл успішно заброньовано на " + time);
        } else {
            System.out.println("Помилка: Столик не знайдено!");
        }

        billManager.showTotalBill(customer);
        System.out.println("--- Обробка завершена ---");
    }

}