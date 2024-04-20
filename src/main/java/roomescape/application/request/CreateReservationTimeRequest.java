package roomescape.application.request;

import java.time.LocalTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
}
