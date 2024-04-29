package roomescape.core.service.response;

import roomescape.core.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponseDto time
) {
    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName().value(),
                reservation.getDate(),
                ReservationTimeResponseDto.from(reservation.getTime()));
    }
}
