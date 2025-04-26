package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;

public interface ReservationTimeRepository {

    ReservationTimes findAll();

    ReservationTime findById(Long id);

    ReservationTime save(ReservationTime reservationTime);

    boolean deleteById(Long id);
}
