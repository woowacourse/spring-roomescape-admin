package roomescape.dto;

import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.time.LocalDate;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeEntity time
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
