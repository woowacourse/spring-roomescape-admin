package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    Long save(ReservationTime reservationTime);

    void delete(Long id);
}
