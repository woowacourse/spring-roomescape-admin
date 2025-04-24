package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    Optional<ReservationTime> findById(Long id);

    void deleteById(Long id);
}
