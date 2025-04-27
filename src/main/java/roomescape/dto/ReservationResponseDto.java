package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain_entity.Reservation;

public record ReservationResponseDto(
        long id, String name, LocalDate date, ReservationTimeResponseDto reservationTime
) {
    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponseDto.from(reservation.getTime())
        );
    }
}
