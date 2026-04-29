package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationResponse fromEntity(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }

    public static List<ReservationResponse> fromEntities(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::fromEntity)
                .toList();
    }
}
