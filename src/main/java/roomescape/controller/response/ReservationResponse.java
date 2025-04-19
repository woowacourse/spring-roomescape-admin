package roomescape.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time) {

    public static ReservationResponse of(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

}
