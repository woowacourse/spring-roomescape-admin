package roomescape.admin.reservation.time;

import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    int delete(Long id);
}
