package roomescape.dao;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakeReservationTimeRepository implements ReservationTimeRepository {
    private final AtomicLong id = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes = new CopyOnWriteArrayList<>();

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(id.getAndIncrement(), reservationTime);
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public boolean existsById(final long id) {
        return reservationTimes.stream().anyMatch(reservationTime -> reservationTime.getId() == id);
    }

    @Override
    public void deleteById(final long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }
}
