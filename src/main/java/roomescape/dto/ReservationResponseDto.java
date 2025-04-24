package roomescape.dto;

import roomescape.entity.ReservationEntity;

import java.time.LocalDate;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponseDto timeDto
) {
    public static ReservationResponseDto from(ReservationEntity reservation) {
        return new ReservationResponseDto(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponseDto.from(reservation.time())
        );
    }
}
