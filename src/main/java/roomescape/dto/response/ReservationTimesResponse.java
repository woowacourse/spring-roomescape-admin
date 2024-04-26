package roomescape.dto.response;

import roomescape.domain.ReservationTime;

import java.util.List;

public record ReservationTimesResponse(List<ReservationTime> times) {
}
