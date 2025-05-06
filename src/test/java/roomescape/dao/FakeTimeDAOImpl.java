package roomescape.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.domain.Time;

public class FakeTimeDAOImpl implements TimeDAO {

    final List<Time> times = new ArrayList<>();

    @Override
    public List<Time> findAllTime() {
        return Collections.unmodifiableList(times);
    }

    @Override
    public Long insertTime(final Time time) {
        times.add(time);
        return (long) times.size();
    }

    @Override
    public int deleteTimeById(final Long id) {
        final long idMatchedCount = times.stream()
                .filter(time -> time.getId().equals(id))
                .count();
        return (int) idMatchedCount;
    }

    @Override
    public boolean existsById(final Long id) {
        return times.stream()
                .anyMatch(time -> time.getId().equals(id));
    }
}
