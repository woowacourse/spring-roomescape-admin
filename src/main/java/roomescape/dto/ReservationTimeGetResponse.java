package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeGetResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {

    public static ReservationTimeGetResponse from(ReservationTime reservationTimeEntity) {
        return new ReservationTimeGetResponse(reservationTimeEntity.getId(), reservationTimeEntity.getStartAt());
    }
}
