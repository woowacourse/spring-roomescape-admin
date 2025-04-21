package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record GetReservationResponse(Long id, String name, LocalDate date, String time) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static GetReservationResponse from(Reservation reservation) {
        return new GetReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime().format(TIME_FORMATTER));
    }
}
