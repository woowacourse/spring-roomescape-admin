package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> reservationTimes;
    private final AtomicLong index;

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new ConcurrentSkipListMap<>();
        this.index = new AtomicLong();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTimeRequest) {
        ReservationTime newReservationTime = new ReservationTime(index.incrementAndGet(), reservationTimeRequest);
        reservationTimes.put(newReservationTime.getId(), newReservationTime);
        return newReservationTime;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.get(id);
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(reservationTimes.values());
    }

    @Override
    public Optional<Integer> deleteById(Long id) {
        if (reservationTimes.containsKey(id)) {
            reservationTimes.remove(id);
            return Optional.of(1);
        } else {
            return Optional.empty();
        }
    }
}
