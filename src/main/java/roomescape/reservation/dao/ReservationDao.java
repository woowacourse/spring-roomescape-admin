package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationDao {

    Reservation insert(Reservation reservation);

    List<Reservation> findAllReservations();

    void delete(Long id);
}
