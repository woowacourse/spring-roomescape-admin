package roomescape.repository;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime time);

    List<ReservationTime> findAll();

    int deleteById(long id);
}
