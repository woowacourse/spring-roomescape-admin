package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record GetReservationResponse(Long id, String name, LocalDate date, String time) {

    public static GetReservationResponse from(Reservation reservation) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return new GetReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime().format(timeFormatter));
    }
}
