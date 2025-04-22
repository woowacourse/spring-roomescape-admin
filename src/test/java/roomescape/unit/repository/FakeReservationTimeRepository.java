package roomescape.unit.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {
    
    @Override
    public long add(ReservationTime reservationTime) {
        return 0;
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
