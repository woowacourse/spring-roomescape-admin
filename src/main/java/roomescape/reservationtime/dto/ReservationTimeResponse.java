package roomescape.reservationtime.dto;

import java.time.LocalTime;
import lombok.Builder;
import roomescape.reservationtime.domain.ReservationTime;

@Builder
public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return ReservationTimeResponse.builder()
                .id(reservationTime.getId())
                .startAt(reservationTime.getStartAt())
                .build();
    }
}
