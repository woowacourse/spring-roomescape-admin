package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;

@Repository
public interface ReservationTimeRepository {

    ReservationTimes findAll();

    ReservationTime findById(Long id);

    ReservationTime save(ReservationTime reservationTime);

    boolean deleteById(Long id);
}
