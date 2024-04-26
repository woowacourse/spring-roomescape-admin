package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    void saveReservationTime(ReservationTime reservationTime);

    List<ReservationTime> findAllReservationTimes();

    void deleteReservationTimeById(long reservationTimeId);
}
