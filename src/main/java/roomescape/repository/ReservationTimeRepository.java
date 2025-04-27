package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime addTime(String start_at);

    List<ReservationTime> getAllTime();

    Integer deleteTime(Long id);

    ReservationTime getReservationTimeById(Long id);
}
