package roomescape.dao;

import java.util.List;
import roomescape.domain.Time;

public interface ReservationTimeDao {

    void save(Time reservationTime);

    List<Time> findAll();

    Time findById(long reservationTimeId);

    void deleteById(long reservationTimeId);
}
