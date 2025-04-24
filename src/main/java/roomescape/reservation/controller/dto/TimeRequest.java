package roomescape.reservation.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.reservation.domain.Time;

public record TimeRequest (@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime startAt) {

    public Time newTime(){
        return new Time(null, startAt);
    }
}
