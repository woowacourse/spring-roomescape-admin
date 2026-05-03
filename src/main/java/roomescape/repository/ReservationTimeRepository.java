package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    Optional<ReservationTime> findById(long id);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);
}
