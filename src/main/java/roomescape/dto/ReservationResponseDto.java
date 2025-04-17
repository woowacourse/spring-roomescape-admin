package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public ReservationResponseDto(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
