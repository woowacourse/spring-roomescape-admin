package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationDetailDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeDetailDto time
) {

    public static ReservationDetailDto from(Reservation reservation) {
        return new ReservationDetailDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeDetailDto.from(reservation.getTime())
        );
    }

}
