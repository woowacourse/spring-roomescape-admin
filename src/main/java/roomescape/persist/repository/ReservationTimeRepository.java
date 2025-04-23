package roomescape.persist.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    ReservationTime add(ReservationTime reservationTime);

    void removeById(long id);
}
