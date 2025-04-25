package roomescape.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.domain.Time;
import roomescape.reservation.domain.repository.TimeRepository;

public class TimeFakeRepository implements TimeRepository {

    private final Map<Long, Time> times = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Long saveAndReturnId(Time time) {
        Long id = idGenerator.incrementAndGet();
        times.put(id, time.withId(id));
        return id;
    }

    @Override
    public List<Time> findAll() {
        return times.values().stream().toList();
    }

    @Override
    public int deleteById(Long id) {
        if (times.containsKey(id)) {
            times.remove(id);
            return 1;
        }
        return 0;
    }

    @Override
    public Time findById(Long id) {
        return times.get(id);
    }

}
