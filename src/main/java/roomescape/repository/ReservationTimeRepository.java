package roomescape.repository;

import java.util.List;
import roomescape.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime createReservationTime(ReservationTime reservationTime);
    List<ReservationTime> readReservationTimes();
    void deleteReservationTime(Long id);
}
