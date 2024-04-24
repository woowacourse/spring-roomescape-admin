package roomescape.repository.reservationtime;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime add(ReservationTime reservationTime);

    void remove(Long id);

    List<ReservationTime> findAll();
}
