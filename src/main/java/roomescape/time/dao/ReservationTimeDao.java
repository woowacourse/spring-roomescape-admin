package roomescape.time.dao;

import java.util.List;
import roomescape.time.domain.ReservationTime;

public interface ReservationTimeDao {

    ReservationTime insert(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    void delete(long id);
}
