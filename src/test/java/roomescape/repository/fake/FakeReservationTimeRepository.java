package roomescape.repository.fake;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.ReservationTime;
import roomescape.ReservationTimes;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {
    private final ReservationTimes reservationTimes = new ReservationTimes();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        ReservationTime createdReservationTime = ReservationTime.generateWithPrimaryKey(reservationTime, id.getAndIncrement());
        reservationTimes.add(createdReservationTime);
        return createdReservationTime;
    }

    @Override
    public List<ReservationTime> readReservationTimes() {
        return reservationTimes.getReservationTimes();
    }

    @Override
    public void deleteReservationTime(Long id) {
        reservationTimes.remove(id);
    }
}
