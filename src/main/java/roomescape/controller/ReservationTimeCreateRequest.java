package roomescape.controller;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(LocalTime startAt) {
}
