package roomescape.repository;

import roomescape.model.ReservationTime;

import java.time.LocalTime;

public interface ReservationTimeRepository {

    boolean existByStartAt(LocalTime startAt);

    ReservationTime insertAndGet(ReservationTime reservationTime);
}
