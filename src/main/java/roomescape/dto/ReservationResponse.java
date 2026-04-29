package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse fromEntity(Reservation reservation) {
        return new ReservationResponse(reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponse.fromEntity(reservation.time()));
    }

    public static List<ReservationResponse> fromEntities(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::fromEntity)
                .toList();
    }
}
