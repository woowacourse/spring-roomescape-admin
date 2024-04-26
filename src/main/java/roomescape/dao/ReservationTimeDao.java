package roomescape.dao;

import roomescape.domain.ReservationTime;
import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {

    Long save(final ReservationTime reservationTime);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(final Long id);

    void deleteById(final Long id);
}
