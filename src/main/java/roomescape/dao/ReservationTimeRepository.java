package roomescape.dao;

import roomescape.entity.ReservationTime;
import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime createReservationTime(ReservationTime reservationTime);

    ReservationTime findReservationTime(Long id);

    List<ReservationTime> findReservationTimes();

    void removeReservationTime(Long id);
}
