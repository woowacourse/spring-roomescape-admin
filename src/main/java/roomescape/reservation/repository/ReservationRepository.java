package roomescape.reservation.repository;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    List<Reservation> findAll();

    int countByDateAndTimeId(LocalDate date, Long timeId);
}
