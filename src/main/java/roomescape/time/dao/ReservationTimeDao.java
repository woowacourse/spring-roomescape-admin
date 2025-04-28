package roomescape.time.dao;

import java.util.List;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

public interface ReservationTimeDao {

    List<ReservationTime> findAllTimes();
    ReservationTime insertTime(final TimeRequest timeRequest);
    void deleteTime(final Long id);
    ReservationTime findReservationTimeById(final Long id);
}
