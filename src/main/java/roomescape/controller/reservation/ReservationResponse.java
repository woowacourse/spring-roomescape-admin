package roomescape.controller.reservation;

import roomescape.controller.time.TimeResponse;

public record ReservationResponse(Long id, String name, String date, TimeResponse time) {
}
