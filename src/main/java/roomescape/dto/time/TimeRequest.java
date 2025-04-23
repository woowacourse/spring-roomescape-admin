package roomescape.dto.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.model.Time;

public record TimeRequest (@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime startAt) {

    public Time toEntity(){
        return new Time(startAt);
    }
}
