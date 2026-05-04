package roomescape.service.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class InMemoryReservationTimeRepository implements ReservationTimeRepository {
    private final Map<Long, ReservationTime> reservationTimes = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values().stream().toList();
    }

    @Override
    public ReservationTime findById(long id) {
        return reservationTimes.get(id);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime saved = new ReservationTime(id.getAndIncrement(), reservationTime.getStartAt());
        reservationTimes.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public void delete(long id) {
        reservationTimes.remove(id);
    }

    @Override
    public boolean isExists(long id) {
        return reservationTimes.containsKey(id);
    }
}
