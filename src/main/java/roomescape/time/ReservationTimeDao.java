package roomescape.time;

import java.util.List;

public interface ReservationTimeDao {
    Long saveTime(ReservationTime reservationTime);
    List<ReservationTime> findAllTime();
    ReservationTime findTimeById(Long id);
    void deleteTimeById(Long id);
}
