package roomescape.admin.repository.time;

import java.util.List;
import java.util.Optional;
import roomescape.admin.domain.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);

    Optional<ReservationTime> findById(Long id);

    List<ReservationTime> findAll();

    void delete(ReservationTime reservationTime);
}
