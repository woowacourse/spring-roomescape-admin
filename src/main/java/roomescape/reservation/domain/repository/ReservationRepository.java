package roomescape.reservation.domain.repository;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation insert(Reservation reservation);

    List<Reservation> findAll();

    int deleteById(Long id);
}
