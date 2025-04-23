package roomescape.dao.resetvationTime;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

public class InMemoryReservationTimeDao implements ReservationTimeDao {

    private final AtomicLong index = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes;

    public InMemoryReservationTimeDao(final List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public long create(final ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTime reservationTime = new ReservationTime(
                index.getAndIncrement(),
                reservationTimeCreateRequest.startAt()
        );
        reservationTimes.add(reservationTime);
        return reservationTime.getId();
    }

    @Override
    public void delete(final Long id) {
        ReservationTime reservationTime = reservationTimes.stream()
                .filter(it -> it.isEqualId(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        reservationTimes.remove(reservationTime);
    }

    @Override
    public ReservationTime findById(final Long id) {
        return reservationTimes.stream()
                .filter(it -> it.isEqualId(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
