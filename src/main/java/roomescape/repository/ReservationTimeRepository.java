package roomescape.repository;

import roomescape.model.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    boolean existByStartAt(LocalTime startAt);

    ReservationTime insertAndReturn(ReservationTime reservationTime);

    int deleteByIdAndCountAffected(Long id);

    ReservationTime findById(Long id);
}
