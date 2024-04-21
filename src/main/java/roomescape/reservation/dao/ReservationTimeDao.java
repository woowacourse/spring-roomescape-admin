package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.dto.request.ReservationTimeRequest;

public interface ReservationTimeDao {

    Long insert(ReservationTimeRequest reservationTimeRequest);
    List<ReservationTime> findAllReservationTimes();
    ReservationTime findById(Long id);
    void delete(Long id);
}
