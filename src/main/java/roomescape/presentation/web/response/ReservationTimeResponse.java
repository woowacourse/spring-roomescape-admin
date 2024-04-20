package roomescape.presentation.web.response;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(

        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public ReservationTimeResponse(ReservationTime time) {
        this(time.getId(), time.getStartAt());
    }
}
