package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.cglib.core.Local;
import roomescape.reservation.entity.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(long id);

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    boolean existsByDateAndTimeId(LocalDate date, long timeId);
}
