package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> reservationTimes = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<ReservationTime> getAll() {
        return reservationTimes.values().stream().toList();
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        if (!reservationTimes.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(reservationTimes.get(id));
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        long id = nextId.getAndIncrement();
        reservationTimes.put(id, reservationTime.withId(id));

        return reservationTime.withId(id);
    }

    @Override
    public void deleteById(long id) {
        ReservationTime removed = reservationTimes.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("존재하지 않는 예약 시간 아이디 입니다. reservationTimeId: " + id);
        }
    }
}
