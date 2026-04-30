package roomescape.reservation.repository;

import roomescape.reservation.Reservation;
import roomescape.reservation.time.ReservationTime;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
