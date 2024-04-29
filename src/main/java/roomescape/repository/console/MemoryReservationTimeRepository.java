package roomescape.repository.console;

import roomescape.domain.reservation.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {
    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, ReservationTime> reservationTimes;

    public MemoryReservationTimeRepository(Map<Long, ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public MemoryReservationTimeRepository() {
        this(new HashMap<>());
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        ReservationTime createdTime = new ReservationTime(counter.getAndIncrement(), reservationTime.getTime());
        reservationTimes.put(createdTime.getId(), createdTime);
        return createdTime;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return Optional.ofNullable(reservationTimes.get(id));
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values()
                .stream()
                .toList();

    }

    @Override
    public void delete(Long id) {
        reservationTimes.remove(id);
    }

}
