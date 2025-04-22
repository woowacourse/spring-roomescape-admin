package roomescape.reservationTime.controller.response;

import java.time.LocalTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

//    public static ReservationTimeResponse from(final Long id, final ReservationTime reservationTime) {
//        return new ReservationTimeResponse(id, reservationTime.getStartAt());
//    }
}
