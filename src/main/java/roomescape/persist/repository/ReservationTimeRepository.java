package roomescape.persist.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(long id);

    ReservationTime add(ReservationTime reservationTime);

    void removeById(long id);

    boolean existsByStartTime(LocalTime localTime);
}
