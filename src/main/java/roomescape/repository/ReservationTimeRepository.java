package roomescape.repository;

import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime findById(Long id);
}
