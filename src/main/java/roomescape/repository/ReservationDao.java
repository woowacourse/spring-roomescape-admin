package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    List<Reservation> findAll();

    Optional<Reservation> findById(long reservationId);

    Reservation save(Reservation reservation);

    void deleteById(long reservationId);
}
