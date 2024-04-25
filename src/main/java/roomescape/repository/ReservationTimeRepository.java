package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();
    ReservationTime findById(Long id);
    ReservationTime save(ReservationTime reservationTime);
    void deleteById(Long id);
}
