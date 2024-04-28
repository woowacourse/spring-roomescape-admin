package roomescape.fake;

import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> reservationTimes;
    private final AtomicLong atomicLong;

    public FakeReservationTimeDao() {
        reservationTimes = new ArrayList<>();
        atomicLong = new AtomicLong(1L);
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findAny();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime newReservationTime =
                new ReservationTime(atomicLong.getAndIncrement(), reservationTime.getStartAt());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public void deleteById(long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }
}
