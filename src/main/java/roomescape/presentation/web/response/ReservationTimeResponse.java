package roomescape.presentation.web.response;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservationTimeResponse(

        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {
}
