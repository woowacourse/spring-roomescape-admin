package roomescape.dao;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTime reservationTime);

    int deleteById(long id);
}
