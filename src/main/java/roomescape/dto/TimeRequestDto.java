package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.Time;

public record TimeRequestDto(
        LocalTime time
) {
    public Time convertToTime(){
        return new Time(this.time);
    }
}
