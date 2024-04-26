package roomescape.dao;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    void save(ReservationTime time);

    void delete(ReservationTime time);
}
