package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    boolean existByDateAndTime(LocalDate date, LocalTime time);

    Reservation insertAndGet(Reservation reservationExcludeIndex);

    int deleteByIdAndCountAffected(Long id);
}
