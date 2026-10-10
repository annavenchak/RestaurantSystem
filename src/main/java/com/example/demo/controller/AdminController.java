package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.CustomerManager;
import com.example.demo.service.MenuManager;
import com.example.demo.service.ReservationManager;
import com.example.demo.service.TableManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Панель адміністратора", description = "Управління столами, меню, перегляд бронювань та клієнтів")
public class AdminController {

    private final TableManager tableManager;
    private final MenuManager menuManager;
    private final ReservationManager resManager;
    private final CustomerManager customerManager;

    public AdminController(TableManager tableManager,
                           MenuManager menuManager,
                           ReservationManager resManager,
                           CustomerManager customerManager) {
        this.tableManager = tableManager;
        this.menuManager = menuManager;
        this.resManager = resManager;
        this.customerManager = customerManager;
    }


    @GetMapping("/tables")
    @Operation(summary = "Переглянути всі столи", description = "Повертає список усіх столів у ресторані.")
    public ResponseEntity<List<Table>> showTables() {
        return ResponseEntity.ok(tableManager.read());
    }

    @PostMapping("/tables")
    @Operation(summary = "Додати новий стіл", description = "Створює стіл обраного типу. Типи: 1 - Regular, 2 - VIP, 3 - Student.")
    public ResponseEntity<String> addTable(
            @RequestParam @Parameter(description = "Тип: 1 (Regular), 2 (VIP), 3 (Student)") int type,
            @RequestParam @Parameter(description = "Назва столика") String name,
            @RequestParam @Parameter(description = "Місткість (кількість людей)") int capacity) {

        if (type < 1 || type > 3) {
            return ResponseEntity.badRequest().body("Помилка: Невірний тип столика.");
        }

        if (type == 1) tableManager.create(new RegularTable(name, "Regular", capacity));
        else if (type == 2) tableManager.create(new VIPTable(name, "VIP", capacity));
        else tableManager.create(new StudentTable(name, "Student Budget", capacity));

        return ResponseEntity.status(HttpStatus.CREATED).body("Стіл успішно створено!");
    }

    @PutMapping("/tables/{id}")
    @Operation(summary = "Оновити інформацію про стіл", description = "Змінює тип, назву або місткість існуючого столика.")
    public ResponseEntity<String> updateTable(
            @PathVariable int id,
            @RequestParam @Parameter(description = "Новий тип: 1 (Regular), 2 (VIP), 3 (Student)") int type,
            @RequestParam @Parameter(description = "Нова назва столика") String name,
            @RequestParam @Parameter(description = "Нова місткість") int capacity) {

        Table t = tableManager.readById(id);
        if (t == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Стіл не знайдено!");
        }

        Table updated;
        if (type == 2) updated = new VIPTable(name, "VIP", capacity);
        else if (type == 3) updated = new StudentTable(name, "Student Budget", capacity);
        else updated = new RegularTable(name, "Regular", capacity);

        updated.setId(id);
        tableManager.update(id, updated);

        return ResponseEntity.ok("Інформацію про стіл оновлено!");
    }

    @DeleteMapping("/tables/{id}")
    @Operation(summary = "Видалити стіл", description = "Видаляє стіл з бази, якщо він не прив'язаний до активних бронювань.")
    public ResponseEntity<String> removeTable(@PathVariable int id) {
        try {
            if (tableManager.delete(id)) {
                return ResponseEntity.ok("Стіл успішно видалено!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Стіл не знайдено!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("ПОМИЛКА: Неможливо видалити стіл. Він прив'язаний до існуючих бронювань.");
        }
    }


    @GetMapping("/menu")
    @Operation(summary = "Переглянути меню", description = "Повертає список усіх страв.")
    public ResponseEntity<List<Menu>> showMenu() {
        return ResponseEntity.ok(menuManager.read());
    }

    @PostMapping("/menu")
    @Operation(summary = "Додати страву", description = "Додає нову страву до меню ресторану.")
    public ResponseEntity<String> addDish(
            @RequestParam String name,
            @RequestParam int grams,
            @RequestParam int price) {

        menuManager.create(new Menu(name, grams, price));
        return ResponseEntity.status(HttpStatus.CREATED).body("Страву успішно додано!");
    }

    @PutMapping("/menu/{id}")
    @Operation(summary = "Редагувати страву", description = "Оновлює назву, вагу та ціну страви.")
    public ResponseEntity<String> editDish(
            @PathVariable int id,
            @RequestParam String name,
            @RequestParam int grams,
            @RequestParam int price) {

        boolean updated = menuManager.update(id, new Menu(name, grams, price));
        if (updated) {
            return ResponseEntity.ok("Страву успішно змінено!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Страву не знайдено!");
    }

    @DeleteMapping("/menu/{id}")
    @Operation(summary = "Видалити страву", description = "Видаляє страву з меню за її ID.")
    public ResponseEntity<String> removeDish(@PathVariable int id) {
        if (menuManager.delete(id)) {
            return ResponseEntity.ok("Страву видалено!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Помилка видалення: страву не знайдено!");
        }
    }


    @GetMapping("/reservations")
    @Operation(summary = "Переглянути всі бронювання", description = "Повертає загальний список усіх бронювань у системі.")
    public ResponseEntity<List<Reservation>> viewAllReservations() {
        return ResponseEntity.ok(resManager.read());
    }

    @GetMapping("/customers")
    @Operation(summary = "Переглянути всіх клієнтів", description = "Повертає список усіх зареєстрованих клієнтів.")
    public ResponseEntity<List<Customer>> viewAllCustomers() {
        return ResponseEntity.ok(customerManager.getAllCustomers());
    }

    @DeleteMapping("/customers")
    @Operation(summary = "Видалити клієнта", description = "Повністю видаляє клієнта та всі його дані за номером телефону.")
    public ResponseEntity<String> removeCustomer(
            @RequestParam @Parameter(description = "Номер телефону клієнта") String phone) {
        try {
            if (customerManager.deleteCustomerCompletely(phone)) {
                return ResponseEntity.ok("Клієнта та всі його дані успішно видалено!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Клієнта з таким номером не знайдено.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Помилка: " + e.getMessage());
        }
    }
}