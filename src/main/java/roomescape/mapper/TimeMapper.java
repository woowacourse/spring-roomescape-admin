package roomescape.mapper;

import roomescape.domain.Time;
import roomescape.dto.TimeResponse;
import roomescape.dto.TimeSaveRequest;

public class TimeMapper {

    public TimeResponse mapToResponse(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public TimeResponse mapToResponse(Long id, Time time) {
        return new TimeResponse(id, time.getStartAt());
    }

    public Time mapToTime(TimeSaveRequest request) {
        return new Time(request.id(), request.startAt());
    }
}
