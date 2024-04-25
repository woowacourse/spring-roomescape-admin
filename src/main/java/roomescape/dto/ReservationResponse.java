package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationResponse(Long id, String name, String date, TimeResponse time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(final Reservation reservation) {
        final ReservationTime time = reservation.getTime();
        final TimeResponse timeResponse = new TimeResponse(time.getId(), time.getStartAt().format(TIME_FORMATTER));
        return new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ISO_DATE), timeResponse);
    }
}
