package roomescape.console.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.dao.ReservationTimeDao;
import roomescape.reservation.domain.ReservationTime;

public class MemoryReservationTimeDao implements ReservationTimeDao {

    private final AtomicLong index;
    private final List<ReservationTime> reservationTimes;

    public MemoryReservationTimeDao() {
        this.index = new AtomicLong(0);
        this.reservationTimes = new ArrayList<>();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        long id = index.incrementAndGet();
        ReservationTime saveReservationTime = new ReservationTime(id, reservationTime.getStartAt());
        reservationTimes.add(saveReservationTime);

        return saveReservationTime;
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimes;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        ReservationTime findReservation = reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findAny()
                .orElse(null);

        return Optional.ofNullable(findReservation);
    }

    @Override
    public void delete(Long id) {
        ReservationTime findReservationTime = findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예약 시간입니다."));

        reservationTimes.remove(findReservationTime);
    }
}
