package roomescape.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import roomescape.domain.entity.ReservationTime;

import java.time.LocalTime;

@Builder
public record ReservationTimeResponse(
        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTime time) {
        return ReservationTimeResponse.builder()
                .id(time.getId())
                .startAt(time.getStartAt())
                .build();
    }
}
