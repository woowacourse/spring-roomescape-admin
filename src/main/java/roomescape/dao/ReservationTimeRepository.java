package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime addAndGet(LocalTime startAt);

    int deleteById(Long id);
}
