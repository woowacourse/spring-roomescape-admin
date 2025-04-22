package roomescape.reservationTime.controller.request;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {

//    public ReservationTime toEntity() {
//        return new ReservationTime(startAt);
//    }
}
