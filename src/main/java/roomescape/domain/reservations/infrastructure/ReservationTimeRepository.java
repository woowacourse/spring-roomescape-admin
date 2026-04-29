package roomescape.domain.reservations.infrastructure;

import java.util.List;
import roomescape.domain.reservations.entity.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime reservation);
    List<ReservationTime> findAll();
    void deleteById(Long id);
}
