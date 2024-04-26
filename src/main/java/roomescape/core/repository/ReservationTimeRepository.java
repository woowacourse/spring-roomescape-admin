package roomescape.core.repository;

import java.util.List;
import java.util.Optional;
import roomescape.core.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    void delete(ReservationTime reservationTime);

    void deleteAll();
}
