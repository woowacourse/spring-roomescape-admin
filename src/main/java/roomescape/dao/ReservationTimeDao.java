package roomescape.dao;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeDao {

    Long insert(ReservationTimeRequest reservationTimeRequest);
    List<ReservationTime> findAllReservationTimes();
    ReservationTime findById(Long id);
    void delete(Long id);
}
