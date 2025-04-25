package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime createReservationTime(ReservationTime reservationTime);
    List<ReservationTime> readReservationTimes();
    Optional<ReservationTime> readReservationTime(Long id);
    void deleteReservationTime(Long id);
}
