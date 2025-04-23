package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.id(), reservationTime.startAt());
    }
}
