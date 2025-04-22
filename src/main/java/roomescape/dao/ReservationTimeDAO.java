package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDAO {
    List<ReservationTime> findAll();

    long insert(ReservationTime reservationTime);

    boolean existsByStartAt(LocalTime startAt);

    boolean deleteById(long id);
}
