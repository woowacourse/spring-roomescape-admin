package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationGetResponse(
        Long id,
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {

    public static ReservationGetResponse from(Reservation reservation) {
        return new ReservationGetResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
