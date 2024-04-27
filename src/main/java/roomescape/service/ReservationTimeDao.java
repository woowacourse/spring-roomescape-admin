package roomescape.service;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTime reservationTime);

    int deleteById(long id);

    void deleteAll();
}
