package roomescape.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationResponse(long id, String name, LocalDate date, ReservationTime time) {
        this(id, name, date, new ReservationTimeResponse(time));
    }

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime())
        );
    }
}
