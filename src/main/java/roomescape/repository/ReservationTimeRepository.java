package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime time);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void delete(Long id);
}
