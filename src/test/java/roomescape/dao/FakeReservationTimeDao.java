package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> times = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(times);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return times.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime persisted = new ReservationTime(
                sequence.incrementAndGet(),
                reservationTime.getStartAt()
        );
        times.add(persisted);
        return persisted;
    }

    @Override
    public int deleteById(Long id) {
        return times.removeIf(time -> time.getId().equals(id)) ? 1 : 0;
    }
}
