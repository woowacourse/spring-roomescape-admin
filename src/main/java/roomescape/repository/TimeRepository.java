package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface TimeRepository {
    Long save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    ReservationTime findById(Long id);

    void deleteById(Long id);
}
