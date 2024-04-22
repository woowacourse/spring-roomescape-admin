package roomescape.admin.reservation.dto.request;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {
}
