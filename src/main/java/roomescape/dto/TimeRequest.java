package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeRequest(LocalTime time) {
    public Time toTime(long id) {
        return new Time(id, time);
    }
}
