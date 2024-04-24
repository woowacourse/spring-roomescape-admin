package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();

    long create(ReservationTime reservationTime);

    boolean deleteById(long id);

    Optional<ReservationTime> findById(long id);

    boolean isExistById(long id);

}
