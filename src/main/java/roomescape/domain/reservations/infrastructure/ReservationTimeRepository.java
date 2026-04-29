package roomescape.domain.reservations.infrastructure;

import java.util.List;
import java.util.Optional;
import roomescape.domain.reservations.entity.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime reservation);
    Optional<ReservationTime> findById(Long id);
    List<ReservationTime> findAll();
    void deleteById(Long id);
}
