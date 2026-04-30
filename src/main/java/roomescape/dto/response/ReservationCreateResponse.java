package roomescape.dto.response;

import roomescape.domain.Reservation;

public record ReservationCreateResponse(
        Long id,
        String name,
        String date,
        TimeResponse timeResponse
) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResponse.from(reservation.getTime())
        );
    }
}
