package roomescape.dao;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation insert(final Reservation reservation);

    boolean deleteById(final Long id);

    boolean duplicateReservationByCustomer(final Reservation reservation, final ReservationTime reservationTime);
}
