package com.example.demo.service;
import com.example.demo.util.DoublyLinkedList;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CustomerReservationManager implements CRUDOperations<Reservation> {
    private ReservationManager rm;
    private TableManager tm;
    @Autowired
    public CustomerReservationManager(ReservationManager rm, TableManager tm) {
        this.rm = rm;
        this.tm = tm;
    }
    @Override
    public void create(Reservation r) {
        rm.create(r);
    }

    @Override
    public List<Reservation> read() {
        return rm.read();
    }

    public DoublyLinkedList<Reservation> read(Customer customer) {
        DoublyLinkedList<Reservation> customerRes = new DoublyLinkedList<>();
        for (Reservation r : rm.read()) {

            if (r.getC().getId() == customer.getId()) {

                customerRes.add(r);
            }
        }
        return customerRes;
    }

    @Override
    public boolean update(int id, Reservation newItem) {
        return rm.update(id, newItem);
    }

    @Override
    public boolean delete(int id) {
        return rm.delete(id);
    }
}
