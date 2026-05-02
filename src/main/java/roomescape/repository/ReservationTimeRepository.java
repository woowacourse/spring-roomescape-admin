package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> getAll();

    Optional<ReservationTime> findById(long id);

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
