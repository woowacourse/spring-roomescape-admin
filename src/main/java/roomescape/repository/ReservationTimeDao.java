package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {
    ReservationTime save(ReservationTime time);

    ReservationTime findById(Long targetId);

    List<ReservationTime> findAll();

    void delete(Long id);
}
