package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationDto(Long id, String name, String date, ReservationTimeDto time) {

    public static ReservationDto from(final Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                ReservationTimeDto.from(reservation.getReservationTime())
        );
    }
}
