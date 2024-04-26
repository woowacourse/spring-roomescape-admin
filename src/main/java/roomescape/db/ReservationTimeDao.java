package roomescape.db;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    ReservationTime save(final ReservationTime reservationTime);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(final long id);

    void deleteById(final long id);
}
