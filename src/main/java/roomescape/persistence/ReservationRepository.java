package roomescape.persistence;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAllByDateAndTime(LocalDate date, LocalTime time);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);
}
