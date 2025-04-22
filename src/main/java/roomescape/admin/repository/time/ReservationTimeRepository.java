package roomescape.admin.repository.time;

import java.time.LocalTime;
import java.util.List;
import roomescape.admin.domain.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);

    ReservationTime getOneById(Long id);

    ReservationTime getOneByStartAt(LocalTime time);

    List<ReservationTime> findAll();

    void delete(ReservationTime reservationTime);
}
