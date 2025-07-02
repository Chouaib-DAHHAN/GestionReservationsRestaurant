package res.restau.resres.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import res.restau.resres.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

   @Query(value = "SELECT r.* FROM reservation r WHERE r.table_id = :tableId AND "
    + "((:dateTime < DATE_ADD(r.date_time, INTERVAL r.duree_minutes MINUTE)) "
    + "AND (DATE_ADD(:dateTime, INTERVAL :dureeMinutes MINUTE) > r.date_time))",
    nativeQuery = true)
List<Reservation> findConflictingReservations(@Param("tableId") Long tableId,
                                            @Param("dateTime") LocalDateTime dateTime,
                                            @Param("dureeMinutes") int dureeMinutes);

}
