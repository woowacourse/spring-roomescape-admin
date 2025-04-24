package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    boolean existByDateAndTimeId(LocalDate date, Long timeId);

    Reservation insertAndGet(Reservation reservation);

    int deleteByIdAndCountAffected(Long id);
}
