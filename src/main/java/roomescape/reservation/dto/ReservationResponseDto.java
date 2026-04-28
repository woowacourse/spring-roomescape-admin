package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.entity.Reservation;

public record ReservationResponseDto(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationResponseDto of(Reservation reservation){
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
