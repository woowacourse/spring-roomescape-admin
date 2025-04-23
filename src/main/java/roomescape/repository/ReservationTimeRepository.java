package roomescape.repository;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    void existsTimeById(long id);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
