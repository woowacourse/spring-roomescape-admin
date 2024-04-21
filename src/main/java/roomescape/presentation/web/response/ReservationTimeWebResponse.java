package roomescape.presentation.web.response;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.ReservationTime;

public record ReservationTimeWebResponse(

        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public ReservationTimeWebResponse(ReservationTime time) {
        this(time.getId(), time.getStartAt());
    }
}
