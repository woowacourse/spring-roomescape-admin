package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    Optional<ReservationTime> save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    int deleteById(long id);

    Optional<ReservationTime> findById(long id);
}
