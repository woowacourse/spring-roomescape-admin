package roomescape.reservation.controller.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.controller.dto.ReservationTimeResponseDto;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        ReservationTimeResponseDto time
) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                ReservationTimeResponseDto.from(reservation.getTime())
        );
    }
}
