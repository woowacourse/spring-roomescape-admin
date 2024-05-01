package roomescape.core.repository;

import roomescape.core.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);

    Optional<ReservationTime> findById(Long id);

    boolean isReferencedId(Long id);
}
