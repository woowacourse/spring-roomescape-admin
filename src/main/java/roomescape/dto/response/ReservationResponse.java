package roomescape.dto.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse toDto(Reservation reservation) {
        System.out.println(reservation.getReservationTime().toString());
        ReservationTimeResponse dto = ReservationTimeResponse.toDto(reservation.getReservationTime());
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), dto);
    }
}
