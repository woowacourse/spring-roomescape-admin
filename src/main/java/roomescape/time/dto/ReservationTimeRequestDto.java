package roomescape.time.dto;

import roomescape.time.domain.ReservationTime;

public record ReservationTimeRequestDto(String startAt) {
    private static final long UNIDENTIFIED_ID = 0;

    public ReservationTime toReservationTime() {
        return new ReservationTime(UNIDENTIFIED_ID, startAt);
    }
}
