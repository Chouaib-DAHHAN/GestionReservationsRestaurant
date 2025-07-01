package res.restau.resres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import res.restau.resres.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
