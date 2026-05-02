package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, Long timeId, String startAt) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.reservationTime().id(),
                reservation.reservationTime().startAt()
        );
    }
}
