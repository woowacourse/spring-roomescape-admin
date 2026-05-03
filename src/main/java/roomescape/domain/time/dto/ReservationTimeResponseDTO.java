package roomescape.domain.time.dto;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

public record ReservationTimeResponseDTO(Long id, LocalTime startAt) {

    public static ReservationTimeResponseDTO from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDTO(reservationTime.getId(), reservationTime.getStartAt());
    }
}
