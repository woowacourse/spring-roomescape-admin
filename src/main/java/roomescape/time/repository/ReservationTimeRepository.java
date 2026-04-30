package roomescape.time.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.time.entity.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(long id);

    Optional<ReservationTime> findById(long id);

    boolean existsByStartAt(LocalTime startAt);

    List<ReservationTime> findAll();

}
