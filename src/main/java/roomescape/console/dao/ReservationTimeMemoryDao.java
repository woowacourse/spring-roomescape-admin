package roomescape.console.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.domain.ReservationTime;
import roomescape.core.domain.ReservationTimeRepository;

public class ReservationTimeMemoryDao implements ReservationTimeRepository {
    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        Long id = counter.incrementAndGet();
        ReservationTime savedReservationTime = new ReservationTime(
                id, reservationTime.getStartAt()
        );
        reservationTimes.add(savedReservationTime);
        return savedReservationTime;
    }

    @Override
    public void delete(ReservationTime reservationTime) {
        reservationTimes.remove(reservationTime);
    }
}
