package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationDao {

    Reservation insert(final Reservation reservation);

    List<Reservation> findAll();

    int deleteById(final Long id);
}
