package roomescape.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.core.service.dto.ReservationTimeServiceRequest;

public record ReservationTimeRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startAt
) {

    public ReservationTimeServiceRequest toReservationTimeServiceRequest() {
        return new ReservationTimeServiceRequest(startAt);
    }
}
