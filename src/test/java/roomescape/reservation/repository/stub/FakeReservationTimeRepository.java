package roomescape.reservation.repository.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    AtomicLong atomicLong = new AtomicLong(1L);
    List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public ReservationTime insertTime(ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(atomicLong.getAndIncrement(),
                reservationTime.getStartAt());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public boolean deleteTimeById(long id) {
        return reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst();
    }
}
