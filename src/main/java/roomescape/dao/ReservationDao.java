package roomescape.dao;

import java.util.List;
import roomescape.data.vo.Reservation;

public interface ReservationDao {

    long save(final Reservation reservation);
    List<Reservation> findAll();
    Reservation findOne(final long id);
    void delete(long id);

}
