package roomescape.dto.request;

import java.time.LocalTime;
import roomescape.valid.annotation.NotNull;

public record ReservationTimeRequest(

        @NotNull
        LocalTime startAt
) {
}
