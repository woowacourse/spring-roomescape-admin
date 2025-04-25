package roomescape.reservation.dao.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

public class FakeReservationTimeDao implements ReservationTimeDao {
    private final List<ReservationTime> reservationTimes;
    private final AtomicLong atomicLong;

    public FakeReservationTimeDao() {
        this.reservationTimes = Collections.synchronizedList(new ArrayList<>());
        this.atomicLong = new AtomicLong(1L);
    }

    @Override
    public List<ReservationTime> findAllTimes() {
        return reservationTimes;
    }

    @Override
    public ReservationTime insertTime(final TimeRequest timeRequest) {
        ReservationTime reservationTime = new ReservationTime(
                atomicLong.getAndIncrement(),
                timeRequest.startAt()
        );

        reservationTimes.add(reservationTime);
        return reservationTime;
    }

    @Override
    public void deleteTime(final Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId().equals(id));
    }

    @Override
    public ReservationTime findReservationTimeById(final Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
