package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeRequest(LocalTime startAt) {
    public Time toTime(long id) {
        return new Time(id, startAt);
    }
}
