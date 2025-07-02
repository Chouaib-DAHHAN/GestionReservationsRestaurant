package res.restau.resres.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import res.restau.resres.model.Reservation;
import res.restau.resres.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<Reservation> getConflictingReservation(Long tableId, LocalDateTime dateTime, int dureeMinutes) {
    List<Reservation> conflicts = reservationRepository.findConflictingReservations(tableId, dateTime, dureeMinutes);
    return conflicts.stream().findFirst();
}

    
}
