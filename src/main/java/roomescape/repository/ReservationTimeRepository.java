package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {

    Long add(ReservationTime reservationTime);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void removeById(Long id);
}
