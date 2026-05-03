package roomescape.control.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponse of(Reservation reservation) {
        final ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                reservation.getReservationTime().getId(),
                timeFormatter.format(reservation.getReservationTime().getReservationTime())
        );
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationName(),
                dateFormatter.format(reservation.getReservationDate()),
                reservationTimeResponse);
    }
}
