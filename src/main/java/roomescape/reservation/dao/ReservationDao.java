package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    boolean removeById(long id);
}
