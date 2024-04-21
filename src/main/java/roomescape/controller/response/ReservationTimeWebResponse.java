package roomescape.controller.response;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.ReservationTime;

public record ReservationTimeWebResponse(

        long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {

    public ReservationTimeWebResponse(ReservationTime time) {
        this(time.getId(), time.getStartAt());
    }
}
