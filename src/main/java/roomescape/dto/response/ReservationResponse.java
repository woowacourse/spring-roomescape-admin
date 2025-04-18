package roomescape.dto.response;

import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(final Reservation reservation) {
        String time = TIME_FORMATTER.format(reservation.time());
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), time);
    }

    public static ReservationResponse from(final ReservationCreateRequest request, Long id) {
        String time = TIME_FORMATTER.format(request.time());
        return new ReservationResponse(id, request.name(), request.date(), time);
    }
}
