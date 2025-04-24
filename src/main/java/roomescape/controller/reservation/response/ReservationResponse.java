package roomescape.controller.reservation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        ReservationTime time) {

    public static ReservationResponse of(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public static ReservationResponse from(final long generatedId, final Reservation reservationRequest) {
        return new ReservationResponse(
                generatedId,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );
    }
}
