package roomescape.time.controller.response;

import java.util.List;
import roomescape.time.domain.Time;

public record TimeResponse(Long id, String startAt) {

    public static List<TimeResponse> from(List<Time> times) {
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }

    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt().toString());
    }
}
