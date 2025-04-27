
package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeResponse(
        long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
}
