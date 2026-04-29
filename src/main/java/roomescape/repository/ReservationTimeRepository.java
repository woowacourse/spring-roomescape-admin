package roomescape.repository;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime findById(long id);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
