package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponseDto(String name, LocalDate date, LocalTime time) {

    public static ReservationResponseDto from(Reservation reservation) {
        if (reservation == null) return null;

        return new ReservationResponseDto(
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
