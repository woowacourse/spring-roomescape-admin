package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime reservationTimeRequest);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    Optional<Integer> deleteById(Long id);
}
