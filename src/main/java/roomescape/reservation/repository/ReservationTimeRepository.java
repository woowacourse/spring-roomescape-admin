package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.model.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime insertTime(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    boolean deleteTimeById(long id);

    Optional<ReservationTime> findById(long id);
}
