package roomescape.time.dao;

import java.util.List;
import roomescape.time.ReservationTime;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();
    ReservationTime save(ReservationTime reservationTime);
    boolean removeById(long id);
    ReservationTime getById(long id);
}
