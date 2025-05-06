package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

public class FakeTimeServiceImpl implements TimeService {

    private final List<Time> times = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1L);

    @Override
    public List<TimeResponse> findAllTime() {
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }

    @Override
    public TimeResponse createTime(final TimeRequest timeRequest) {
        Time time = timeRequest.toEntity();
        time.setId(atomicLong.getAndIncrement());
        times.add(time);
        return TimeResponse.from(time);
    }

    @Override
    public int deleteTimeById(final Long id) {
        int beforeSize = times.size();
        times.removeIf(time -> time.getId()
                .equals(id));
        int afterSize = times.size();
        return beforeSize - afterSize;
    }

    @Override
    public boolean existsById(final Long id) {
        return times.stream()
                .anyMatch(time -> time.getId().equals(id));
    }
}
