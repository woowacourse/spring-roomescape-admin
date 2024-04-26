package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationDao {

    Reservation insert(final Reservation reservation);

    public List<Reservation> findAll();

    public int deleteById(final Long id);
}
