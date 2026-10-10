package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.Table;
import com.example.demo.service.BillManager;
import com.example.demo.service.CustomerManager;
import com.example.demo.service.ReservationManager;
import com.example.demo.service.RestaurantFacade;
import com.example.demo.service.TableManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@Tag(name = "Клієнтський сервіс ресторану", description = "Ендпойнти для перегляду столиків, створення, зміни та видалення бронювань, а також виведення рахунку")
public class RestaurantController {

    private final RestaurantFacade restaurantFacade;
    private final TableManager tableManager;
    private final ReservationManager reservationManager;
    private final CustomerManager customerManager;
    private final BillManager billManager;

    public RestaurantController(RestaurantFacade restaurantFacade,
                                TableManager tableManager,
                                ReservationManager reservationManager,
                                CustomerManager customerManager,
                                BillManager billManager) {
        this.restaurantFacade = restaurantFacade;
        this.tableManager = tableManager;
        this.reservationManager = reservationManager;
        this.customerManager = customerManager;
        this.billManager = billManager;
    }

    @GetMapping("/tables")
    @Operation(summary = "Отримати список усіх столиків", description = "Повертає перелік столів із їхніми ID, категоріями, назвами та місткістю для вибору перед бронюванням.")
    public ResponseEntity<List<Table>> getAllTables() {
        List<Table> tables = tableManager.read();
        return ResponseEntity.ok(tables);
    }

    @PostMapping("/booking")
    @Operation(summary = "Створити комплексне бронювання столика із замовленням", description = "Реєструє клієнта, прив'язує обраний столик за ID, додає початкову страву з меню та генерує рахунок.")
    public ResponseEntity<String> createBooking(
            @RequestParam @Parameter(description = "Ім'я клієнта") String customerName,
            @RequestParam @Parameter(description = "Номер телефону") String phone,
            @RequestParam @Parameter(description = "ID столика") int tableId,
            @RequestParam @Parameter(description = "ID страви з меню") int menuId,
            @RequestParam @Parameter(description = "Кількість порцій") int quantity,
            @RequestParam @Parameter(description = "Дата (наприклад, 2026.05.20)") String date,
            @RequestParam @Parameter(description = "Час (наприклад, 19:00)") String time,
            @RequestParam @Parameter(description = "Кількість гостей") int guests) {

        Table table = tableManager.read().stream()
                .filter(t -> t.getId() == tableId)
                .findFirst()
                .orElse(null);

        if (table == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Помилка: Столик з ID " + tableId + " не знайдено в системі.");
        }

        restaurantFacade.bookTableAndOrder(customerName, phone, tableId, menuId, quantity, date, time, guests);
        return ResponseEntity.status(HttpStatus.CREATED).body("Комплексне бронювання успішно створено!");
    }

    @PutMapping("/booking/{id}")
    @Operation(summary = "Змінити параметри існуючого бронювання", description = "Дозволяє оновити дату, час, кількість гостей та тривалість візиту для активного бронювання.")
    public ResponseEntity<String> updateBooking(
            @PathVariable @Parameter(description = "ID бронювання, яке потрібно змінити") int id,
            @RequestParam @Parameter(description = "Нова дата") String date,
            @RequestParam @Parameter(description = "Новий час") String time,
            @RequestParam @Parameter(description = "Нова кількість гостей") int guests,
            @RequestParam @Parameter(description = "Нова кількість годин оренди") int hours) {

          Reservation reservation = reservationManager.read().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Бронювання з ID " + id + " не знайдено.");
        }

        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setGuests(guests);
        reservation.setHours(hours);

        boolean isUpdated = reservationManager.update(id, reservation);
        if (isUpdated) {
            return ResponseEntity.ok("Бронювання №" + id + " успішно оновлено.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не вдалося оновити бронювання.");
        }
    }

    @DeleteMapping("/booking/{id}")
    @Operation(summary = "Видалити бронювання", description = "Повністю видаляє запис про бронювання столика з бази даних за його ідентифікатором.")
    public ResponseEntity<String> deleteBooking(
            @PathVariable @Parameter(description = "ID бронювання для скасування") int id) {

        boolean isDeleted = reservationManager.delete(id);
        if (isDeleted) {
            return ResponseEntity.ok("Бронювання №" + id + " успішно скасовано та видалено.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Помилка: Бронювання з ID " + id + " не знайдено, тому його не можна видалити.");
        }
    }

    @GetMapping("/bill")
    @Operation(summary = "Показати рахунок клієнта", description = "Знаходить клієнта за номером телефону та викликає BillManager для розрахунку вартості страв і оренди.")
    public ResponseEntity<String> showBill(
            @RequestParam @Parameter(description = "Номер телефону клієнта") String phone) {


        Customer customer = customerManager.read().stream()
                .filter(c -> c.getPhone().equals(phone))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Клієнта з номером телефону " + phone + " не знайдено в системі.");
        }


        billManager.showTotalBill(customer);
        return ResponseEntity.ok("Рахунок для клієнта " + customer.getName() + " успішно сформовано та виведено в консоль сервера.");
    }
}
