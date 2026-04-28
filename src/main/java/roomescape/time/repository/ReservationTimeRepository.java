package roomescape.time.repository;

import java.util.List;
import java.util.Optional;
import roomescape.time.entity.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> finaAll();

    Optional<ReservationTime> findById(Long id);

    void deleteById(Long id);

}
