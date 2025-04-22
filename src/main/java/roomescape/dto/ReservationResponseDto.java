package roomescape.dto;

import roomescape.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationResponseDto from(ReservationEntity reservation) {
        return new ReservationResponseDto(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }
}
