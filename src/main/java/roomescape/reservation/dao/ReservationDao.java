package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAllReservations();

    Reservation insert (Reservation reservation);

    void delete(Long id);
}
