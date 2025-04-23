package roomescape.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeTimeDao implements TimeDao {

    private static Long NEXT_ID = 1L;
    private final List<Time> times = new ArrayList<>();
    private final List<Long> invokeDeleteId = new ArrayList<>();


    @Override
    public Time saveTime(final Time time) {
        final Time savedTime = time.writeId(NEXT_ID++);
        times.add(savedTime);
        return savedTime;
    }

    @Override
    public List<Time> findAllTime() {
        return new ArrayList<>(times);
    }

    @Override
    public Time findTimeById(final Long id) {
        return times.stream()
                .filter(time -> Objects.equals(time.id(), id))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean existTimeById(final Long id) {
        return times.stream()
                .anyMatch(time -> Objects.equals(time.id(), id));
    }

    @Override
    public void deleteTimeById(final Long id) {
        times.stream()
                .filter(time -> Objects.equals(time.id(), id))
                .findAny()
                .ifPresent(time -> times.remove(time));
    }
}
