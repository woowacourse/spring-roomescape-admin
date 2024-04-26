package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();

    long save(String name, String date, long timeId);

    boolean deleteById(long id);
}
