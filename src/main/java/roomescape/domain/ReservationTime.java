package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {

    public static ReservationTime ofNew(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }
}
