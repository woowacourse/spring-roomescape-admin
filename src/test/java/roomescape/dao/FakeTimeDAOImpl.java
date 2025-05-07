package roomescape.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Time;

public class FakeTimeDAOImpl implements TimeDAO {

    private final List<Time> times = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1L);

    @Override
    public List<Time> findAllTime() {
        return Collections.unmodifiableList(times);
    }

    @Override
    public Long insertTime(final Time time) {
        final long id = atomicLong.getAndIncrement();
        time.setId(id);
        times.add(time);
        return id;
    }

    @Override
    public int deleteTimeById(final Long id) {
        final int beforeSize = times.size();
        times.removeIf(time -> time.getId().equals(id));
        final int afterSize = times.size();
        return beforeSize - afterSize;
    }

    @Override
    public boolean existsById(final Long id) {
        return times.stream()
                .anyMatch(time -> time.getId().equals(id));
    }
}
