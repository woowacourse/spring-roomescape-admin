package roomescape.repository;

import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    long save(ReservationTime reservationTime);
}
