package roomescape.admin.reservation.service.port;

import java.util.List;
import roomescape.admin.reservation.entity.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    int delete(Long id);
}
