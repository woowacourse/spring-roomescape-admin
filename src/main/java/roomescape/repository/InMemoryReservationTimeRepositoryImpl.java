package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationTimeRepositoryImpl implements ReservationTimeRepository {
    private final List<ReservationTime> reservationTimes = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(0);

    public ReservationTime save(final ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(
                index.incrementAndGet(),
                reservationTime.getStartAt());
        reservationTimes.add(newReservationTime);

        return newReservationTime;
    }

    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    public Optional<ReservationTime> findById(final Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.hasId(id))
                .findFirst();
    }

    public void deleteById(final long id) {
        reservationTimes.stream()
                .filter(reservationTime -> reservationTime.hasId(id))
                .findFirst()
                .ifPresent(reservationTimes::remove);
    }

    public void deleteAll() {
        reservationTimes.clear();
    }
}
