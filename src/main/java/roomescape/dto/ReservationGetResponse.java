package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationGetResponse(Long id, String name, LocalDate date, String time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationGetResponse from(Reservation reservation) {
        return new ReservationGetResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime().format(TIME_FORMATTER));
    }
}
