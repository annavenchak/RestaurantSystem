package com.example.demo;

import com.example.demo.domain.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationManagerTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationManager reservationManager;

    @Test
    void testReadById_Found() {
        Reservation mockRes = new Reservation();
        when(reservationRepository.findById(1)).thenReturn(Optional.of(mockRes));

        Reservation result = reservationManager.readById(1);

        assertNotNull(result);
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    void testDelete_Success() {
        when(reservationRepository.existsById(5)).thenReturn(true);

        boolean deleted = reservationManager.delete(5);

        assertTrue(deleted);
        verify(reservationRepository).deleteById(5);
    }
}