package roomescape.presentation.web.request;

import java.time.LocalTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
}
