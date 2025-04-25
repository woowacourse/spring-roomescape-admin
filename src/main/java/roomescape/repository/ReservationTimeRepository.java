package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(final ReservationTime reservationTime);
    List<ReservationTime> findAll();
    int deleteById(final long id);
    Optional<ReservationTime> findById(final long id);
}
