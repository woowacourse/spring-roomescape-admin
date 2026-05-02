package roomescape.domain;

import roomescape.controller.ReservationRequest;

public record Reservation(long id, String name, String date, long reservationTimeId) {

    public static Reservation transientOf(ReservationRequest reservationRequest) {
        return new Reservation(0, reservationRequest.name(), reservationRequest.date(), reservationRequest.timeId());
    }
}
