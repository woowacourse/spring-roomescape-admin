package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime reservationTime);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
