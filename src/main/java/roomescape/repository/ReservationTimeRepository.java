package roomescape.repository;

import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    Optional<ReservationTime> findById(Long id);
}
