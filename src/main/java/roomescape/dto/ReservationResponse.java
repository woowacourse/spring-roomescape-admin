package roomescape.dto;

import roomescape.entity.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
