package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

public record ReservationResponseDTO(Long id, String name, LocalDate date, ReservationTime time) {

    public static ReservationResponseDTO from(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
