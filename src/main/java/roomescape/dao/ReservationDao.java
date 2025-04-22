package roomescape.dao;

import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation insert(final Reservation reservation);

    boolean deleteById(final Long id);
}
