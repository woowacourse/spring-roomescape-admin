package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, String time) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationResponse(long id, String name, LocalDate date, LocalTime time) {
        this(id, name, date, time.format(FORMATTER));
    }

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime().format(FORMATTER));
    }

}
