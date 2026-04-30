package roomescape.domain.time.repository;

import java.util.ArrayList;
import java.util.List;
import roomescape.domain.time.domain.Time;

public class FakeTimeRepository implements TimeRepository {

    private final List<Time> times = new ArrayList<>();
    private Long sequence = 1L;

    @Override
    public Time save(Time time) {
        Time savedTime = new Time(sequence++, time.getStartAt());
        times.add(savedTime);
        return savedTime;
    }

    @Override
    public List<Time> findAllTimes() {
        return times;
    }

    @Override
    public Time findTimeById(Long id) {
        return times.stream()
            .filter(time -> time.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void deleteTimeById(Long id) {
        times.removeIf(time -> time.getId().equals(id));
    }
}
