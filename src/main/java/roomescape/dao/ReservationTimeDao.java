package roomescape.dao;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    ReservationTime save(ReservationTime reservationTime);

    int deleteById(Long id);
}