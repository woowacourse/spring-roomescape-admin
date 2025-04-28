package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

public class TestTimeServiceImpl implements TimeService {

    private final List<Time> times = new ArrayList<>();

    @Override
    public List<TimeResponse> findAllTime() {
        return times.stream()
                .map(TimeResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public TimeResponse createTime(final TimeRequest timeRequest) {
        Time time = timeRequest.toEntity();
        times.add(time);
        time.setId(times.size() + 1L);
        return TimeResponse.from(time);
    }

    @Override
    public int deleteTimeById(final Long id) {
        int count = (int) times.stream()
                .filter(time -> time.getId().equals(id))
                .count();
        return count;
    }
}
