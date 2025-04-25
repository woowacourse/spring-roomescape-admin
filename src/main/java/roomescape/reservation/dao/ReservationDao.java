package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAllReservations();

    Reservation insertReservation(final Reservation reservation);

    void removeReservation(final long id);
}
