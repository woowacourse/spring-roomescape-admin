package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;

public record ReservationDto(Long id, String name, LocalDate date, ReservationTimeDto time) {
    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeDto.from(reservation.getTime())
        );
    }
}
