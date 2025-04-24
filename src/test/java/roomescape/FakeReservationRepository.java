package roomescape;

import roomescape.repository.ReservationRepository;

public interface FakeReservationRepository extends ReservationRepository {
    void clear();
}
