package roomescape.reservation.dto.response;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationtime.dto.response.ReservationTimeResponse;

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
