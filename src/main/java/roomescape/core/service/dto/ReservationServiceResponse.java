package roomescape.core.service.dto;

import java.time.LocalDate;
import roomescape.core.domain.Reservation;

public record ReservationServiceResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeServiceResponse time
) {

    public static ReservationServiceResponse from(Reservation reservation) {
        return new ReservationServiceResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeServiceResponse.from(reservation.getTime())
        );
    }
}
