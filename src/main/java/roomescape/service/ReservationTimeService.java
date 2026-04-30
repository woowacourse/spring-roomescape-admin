package roomescape.service;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeService {

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void delete(Long id);
}
