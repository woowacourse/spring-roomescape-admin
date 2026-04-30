package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponseDto(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime start_at
) {
    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        if (reservationTime == null) return null;

        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
