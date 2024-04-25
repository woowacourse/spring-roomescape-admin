package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(long id);

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
