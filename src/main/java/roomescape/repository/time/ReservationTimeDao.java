package roomescape.repository.time;

import java.util.List;
import java.util.Optional;
import roomescape.domain.time.ReservationTime;

public interface ReservationTimeDao {

    Optional<ReservationTime> findById(long id);

    void save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    boolean deleteById(long id);
}
