package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.ReservationTime;

@Repository
public interface ReservationTimeRepository {
    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    boolean delete(long timeId);
}
