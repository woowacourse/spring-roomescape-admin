package roomescape.web.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.core.domain.ReservationTime;

public record ReservationTimeRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
