package roomescape.repository.collection;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {
    private final List<ReservationTime> reservationTimes = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime saved = new ReservationTime(
                counter.getAndIncrement(),
                reservationTime.getStartAt()
        );
        reservationTimes.add(saved);
        return saved;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        reservationTimes.removeIf(time -> time.getId().equals(id));
    }
}
