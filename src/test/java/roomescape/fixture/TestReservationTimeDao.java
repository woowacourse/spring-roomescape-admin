package roomescape.fixture;

import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TestReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> reservationTimes;
    private final AtomicLong autoIncrement = new AtomicLong(0);

    public TestReservationTimeDao(List<ReservationTime> reservationTimes) {
        this.reservationTimes = new ArrayList<>(reservationTimes);
    }

    @Override
    public Optional<ReservationTime> select(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> Objects.equals(reservationTime.getId(), id))
                .findFirst();
    }

    @Override
    public List<ReservationTime> selectAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public ReservationTime insert(ReservationTime reservationTime) {
        ReservationTime reservationTimeEntity = new ReservationTime(
                autoIncrement.incrementAndGet(),
                reservationTime.getStartAt()
        );
        reservationTimes.add(reservationTimeEntity);
        return reservationTimeEntity;
    }

    @Override
    public boolean delete(Long id) {
        return reservationTimes.removeIf(
                reservationTime -> Objects.equals(reservationTime.getId(), id)
        );
    }

}
