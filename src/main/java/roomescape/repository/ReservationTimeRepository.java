package roomescape.repository;

import roomescape.entity.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    Long add(final ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(final Long id);

    Optional<ReservationTime> findById(final Long id);
}
