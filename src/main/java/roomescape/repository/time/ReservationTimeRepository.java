package roomescape.repository.time;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAllReservationTimes();

    ReservationTime insertReservationTime(ReservationTime reservationTime);

    void deleteReservationTimeById(long id);
}
