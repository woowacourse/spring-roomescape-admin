package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {
    Long createTime(ReservationTime reservationTime);

    List<ReservationTime> readTimes();

    int deleteTime(Long id);
}
