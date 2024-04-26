package roomescape.dao;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    void save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(long reservationTimeId);
}
