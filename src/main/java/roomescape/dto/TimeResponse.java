package roomescape.dto;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Time;

public record TimeResponse(long id, LocalTime startAt) {

    public static TimeResponse toResponse(long id, TimeRequest timeRequest) {
        return new TimeResponse(id, timeRequest.startAt());
    }

    public static List<TimeResponse> toResponses(List<Time> times) {
        return times.stream()
                .map(time -> new TimeResponse(time.getId(), time.getStartAt()))
                .toList();
    }
}
