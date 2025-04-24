package roomescape;

import roomescape.repository.ReservationTimeRepository;

public interface FakeReservationTimeRepository extends ReservationTimeRepository {
    void clear();
}
