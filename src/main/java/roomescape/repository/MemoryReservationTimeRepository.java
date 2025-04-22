package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTime.withId(index.getAndIncrement());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.isEqualId(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.isEqualId(id));
    }
}
