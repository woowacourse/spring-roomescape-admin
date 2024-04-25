package roomescape.repository.reservationtime;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    void delete(Long id);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();
}
