package roomescape.admin.controller.dto;

import java.time.LocalTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {
    
}
