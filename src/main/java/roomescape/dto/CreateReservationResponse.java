package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record CreateReservationResponse(int id, String name, LocalDate date, ReservationTime reservationTime) {

    public static CreateReservationResponse from(Reservation reservation) {
        return new CreateReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
