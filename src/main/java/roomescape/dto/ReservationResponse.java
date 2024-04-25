package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(final Reservation reservation) {
        final ReservationTime time = reservation.getTime();
        final ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(time.getId(),
                time.getStartAt().format(TIME_FORMATTER));
        return new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ISO_DATE), reservationTimeResponse);
    }
}
