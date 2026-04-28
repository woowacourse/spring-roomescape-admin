package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.Reservation;

public record ReservationResponseDto(
    long id,
    String name,
    String date,
    String time
) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponseDto from(final Reservation reservation) {
        return new ReservationResponseDto(
            reservation.getId(),
            reservation.getName(),
            DATE_FORMATTER.format(reservation.getDate()),
            TIME_FORMATTER.format(reservation.getTime()));
    }
}
