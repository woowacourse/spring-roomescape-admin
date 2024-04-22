package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeAddRequest {
    private LocalTime startAt;

    public ReservationTimeAddRequest() {

    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
