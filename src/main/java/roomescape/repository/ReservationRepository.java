package roomescape.repository;

import roomescape.domain.reservation.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    void delete(Long id);

    List<Reservation> findAllByDateTime(Reservation reservation);
}
