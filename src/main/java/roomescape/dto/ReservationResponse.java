package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        String date,
        String time) {
    public ReservationResponse(Reservation reservationEntity) {
        this(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(), reservationEntity.getTime());
    }
}
