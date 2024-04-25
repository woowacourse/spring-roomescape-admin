package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.dao.ReservationTime;

public record ReservationTimeSaveRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(null, this.startAt);
    }
}
