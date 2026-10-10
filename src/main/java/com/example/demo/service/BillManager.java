package com.example.demo.service;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Reservation;
import org.springframework.stereotype.Service;
@Service
public class BillManager {

    public void showTotalBill(Customer customer) {
        System.out.println("===Order details for " + customer.getName() + "===");
        double foodTotal = customer.getTotalBill();
        System.out.println("Food total: $" + foodTotal);
    }
    public void showTotalBill(Customer customer, Reservation r) {
            ReceiptPrinter printer = new ReceiptPrinter(customer, r);
            printer.printReceipt();
        }

        private class ReceiptPrinter {
            private Customer customer;
            private Reservation reservation;

            public ReceiptPrinter(Customer customer, Reservation reservation) {
                this.customer = customer;
                this.reservation = reservation;
            }

            public void printReceipt() {
                System.out.println("====================================");
                System.out.println("          OFFICIAL RECEIPT          ");
                System.out.println("====================================");
                System.out.println("Customer: " + customer.getName());

                double roomTotal = reservation.getTotalPrice();
                double foodTotal = customer.getTotalBill();

                System.out.println("Table booking: $" + roomTotal);
                System.out.println("Food & Drinks: $" + foodTotal);
                System.out.println("------------------------------------");
                System.out.println("TOTAL TO PAY: $" + (roomTotal + foodTotal));
                System.out.println("====================================");
            }
        }

}
