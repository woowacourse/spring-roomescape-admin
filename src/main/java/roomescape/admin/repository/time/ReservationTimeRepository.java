package roomescape.admin.repository.time;

import java.util.List;
import roomescape.admin.domain.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);

    ReservationTime getOneById(Long id);

    List<ReservationTime> findAll();

    void delete(ReservationTime reservationTime);
}
