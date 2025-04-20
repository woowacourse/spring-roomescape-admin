package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationDto(
        long id,
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ReservationDto toDto(final Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
