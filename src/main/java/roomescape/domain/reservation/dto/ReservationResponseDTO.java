package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.dto.ReservationTimeResponseDTO;

public record ReservationResponseDTO(Long id, String name, LocalDate date, ReservationTimeResponseDTO time) {

    public static ReservationResponseDTO from(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponseDTO.from(reservation.getTime())
        );
    }
}
