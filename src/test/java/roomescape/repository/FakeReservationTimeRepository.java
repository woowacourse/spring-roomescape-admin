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
    public int deleteById(long id) {
        ReservationTime deleteReservation = reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.id(), id))
                .findFirst().get();

        int affectedRows = (int) reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.id(), id))
                .count();

        if (affectedRows > 0) {
            reservationTimes.remove(deleteReservation);
        }
        return affectedRows;
    }

    @Override
    public ReservationTime findById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No ReservationTime with id: " + id));
    }
}
