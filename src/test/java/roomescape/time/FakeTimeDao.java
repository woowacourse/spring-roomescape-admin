package roomescape.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeTimeDao implements TimeDao {

    private final List<Time> times = new ArrayList<>();
    private final List<Long> invokeDeleteId = new ArrayList<>();
    private Long NEXT_ID = 1L;

    @Override
    public Long saveTime(final Time time) {
        final Time saveTime = new Time(NEXT_ID++, time.startAt());
        times.add(saveTime);
        return saveTime.id();
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
    public void deleteTimeById(final Long id) {
        times.stream()
                .filter(time -> Objects.equals(time.id(), id))
                .findAny()
                .ifPresent(time -> times.remove(time));

        invokeDeleteId.add(id);
    }

    public boolean isInvokeDeleteId(final Long id) {
        return invokeDeleteId.stream()
                .anyMatch(timeId -> Objects.equals(timeId, id));
    }
}
