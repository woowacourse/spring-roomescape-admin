package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime createReservationTime(ReservationTime reservationTime);
    List<ReservationTime> readReservationTimes();
    ReservationTime readReservationTime(Long id);
    void deleteReservationTime(Long id);
}
