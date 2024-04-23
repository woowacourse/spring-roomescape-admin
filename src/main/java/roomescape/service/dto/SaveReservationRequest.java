package roomescape.service.dto;

import roomescape.domain.Reservation;

public record SaveReservationRequest(String name, String date, String time) {

    public static Reservation toEntity(SaveReservationRequest request) {
        return new Reservation(request.name(), request.date(), request.time());
    }
}
