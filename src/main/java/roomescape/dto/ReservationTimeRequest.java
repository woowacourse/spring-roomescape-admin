package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public record ReservationTimeRequest(@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")LocalTime startAt) {

    public ReservationTime toEntity() {

        return new ReservationTime(null, startAt);
    }
}
