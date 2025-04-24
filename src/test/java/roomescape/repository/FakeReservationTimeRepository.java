package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes;
    private final AtomicLong reservationTimeId = new AtomicLong(1);

    public FakeReservationTimeRepository(final List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(reservationTimeId.getAndIncrement(), reservationTime.startAt());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public void deleteById(Long id) {
        ReservationTime deleteReservation = reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException());

        reservationTimes.remove(deleteReservation);
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No ReservationTime with id: " + id));
    }
}
