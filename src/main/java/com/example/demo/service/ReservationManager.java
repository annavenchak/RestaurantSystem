package com.example.demo.service;

import com.example.demo.domain.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationManager implements CRUDOperations<Reservation> {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationManager(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    @Override
    public void create(Reservation res) {
        reservationRepository.save(res);
    }

    @Override
    public List<Reservation> read() {
        return reservationRepository.findAll();
    }

    public Reservation readById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(int id, Reservation newItem) {
        if (reservationRepository.existsById(id)) {
            newItem.setId(id);
            reservationRepository.save(newItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public int getNextId() {
        return 0;
    }


}