package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.reservation.entity.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    Optional<Reservation> findById(long id);
}
