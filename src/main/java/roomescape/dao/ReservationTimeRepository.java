package roomescape.dao;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    boolean existsTimeById(long id);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
