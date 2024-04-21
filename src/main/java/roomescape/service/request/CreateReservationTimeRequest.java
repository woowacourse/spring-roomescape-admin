package roomescape.service.request;

import java.time.LocalTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
}
