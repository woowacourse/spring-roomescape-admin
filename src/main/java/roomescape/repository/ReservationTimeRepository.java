package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    ReservationTime add(ReservationTime reservationTime);

    void removeById(long id);
}
