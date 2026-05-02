package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    long insert(LocalTime startAt);

    void deleteById(long timeId);

    ReservationTime findById(long timeId);
}
