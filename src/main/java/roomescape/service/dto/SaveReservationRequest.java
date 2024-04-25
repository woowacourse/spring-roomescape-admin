package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record SaveReservationRequest(String name, LocalDate date, Long reservationTimeId) {

    public static Reservation toEntity(SaveReservationRequest request, ReservationTime reservationTime) {
        return new Reservation(request.name(), request.date(), reservationTime);
    }
}
