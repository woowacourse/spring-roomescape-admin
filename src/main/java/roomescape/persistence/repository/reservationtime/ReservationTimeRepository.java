package roomescape.persistence.repository.reservationtime;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    Long addAndGetId(ReservationTime reservationTime);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
