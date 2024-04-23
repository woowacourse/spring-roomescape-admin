package roomescape.dao;

import java.util.List;
import roomescape.Reservation;

public interface ReservationDao {

    long save(final Reservation reservation);
    List<Reservation> findAll();
    void delete(long id);
}
