package roomescape.repository;

import roomescape.model.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    boolean existByStartAt(LocalTime startAt);

    ReservationTime insertAndGet(ReservationTime reservationTime);

    int deleteByIdAndCountAffected(Long id);

    ReservationTime findById(Long id);
}
