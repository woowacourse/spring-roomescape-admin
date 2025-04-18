package roomescape.dto.response;

import roomescape.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {

    public static ReservationTimeResponse from(final ReservationTime times) {
        return null;
    }

    public static ReservationTimeResponse from(final ReservationTimeCreateRequest request, final long savedId) {
        return null;
    }
}
