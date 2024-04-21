package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.ReservationTime;

public interface ReservationTimeDao {

    ReservationTime insert(ReservationTime reservationTime);
    List<ReservationTime> findAllReservationTimes();
    ReservationTime findById(Long id);
    void delete(Long id);
}
