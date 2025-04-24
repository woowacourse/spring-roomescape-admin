package roomescape.dto.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        TimeResponse time
) {
    public static ReservationResponse toDto(Reservation reservation) {
        System.out.println(reservation.getReservationTime().toString());
        TimeResponse dto = TimeResponse.toDto(reservation.getReservationTime());
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), dto);
    }
}
