package roomescape.reservation.repository;

import roomescape.reservation.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    int deleteById(long id);
}
