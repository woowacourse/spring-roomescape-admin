package roomescape.time;

import java.util.List;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();
    ReservationTime save(ReservationTime reservationTime);
    boolean removeById(long id);
    ReservationTime getById(long id);
}
