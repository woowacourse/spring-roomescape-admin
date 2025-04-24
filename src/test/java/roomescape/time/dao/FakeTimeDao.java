package roomescape.time.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.time.domain.Time;

public class FakeTimeDao implements TimeDao {

    private final List<Time> times = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public Time insert(Time time) {
        Time savedTime = new Time(index.getAndIncrement(), time);
        times.add(savedTime);
        return savedTime;
    }

    @Override
    public List<Time> findAll() {
        return new ArrayList<>(times);
    }

    @Override
    public void delete(long id) {
        Time targetTime = times.stream()
                .filter(time -> Objects.equals(time.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("데이터베이스에 해당 id가 존재하지 않습니다."));

        times.remove(targetTime);
    }
}
