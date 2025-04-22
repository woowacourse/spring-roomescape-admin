package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation insertAndGet(Reservation reservationExcludeIndex);

    void deleteById(Long id);

    boolean existByDateAndTime(LocalDate date, LocalTime time);
}
