package roomescape.core.repository;

import java.util.List;
import roomescape.core.model.ReservationTime;

public interface ReservationTimeRepository {
    Long createTime(ReservationTime reservationTime);

    List<ReservationTime> readTimes();

    int deleteTime(Long id);
}
