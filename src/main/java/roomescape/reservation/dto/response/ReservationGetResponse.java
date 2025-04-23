package roomescape.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.reservation.entity.ReservationEntity;

public record ReservationGetResponse(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationGetResponse from(ReservationEntity reservation) {
        return new ReservationGetResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public static List<ReservationGetResponse> from(List<ReservationEntity> reservations) {
        return reservations.stream()
                .map(ReservationGetResponse::from)
                .toList();
    }
}