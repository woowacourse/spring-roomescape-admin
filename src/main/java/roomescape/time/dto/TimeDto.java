package roomescape.time.dto;

import java.time.LocalTime;
import roomescape.time.Time;

public record TimeDto(String startAt) {
    public Time createTime() {
        return new Time(LocalTime.parse(startAt));
    }
}
