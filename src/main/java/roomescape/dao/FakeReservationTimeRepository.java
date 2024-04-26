package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

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
    public void deleteById(final long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst();
    }
}
