package roomescape.dao;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(Reservation reservation);

    int deleteById(long id);
}
