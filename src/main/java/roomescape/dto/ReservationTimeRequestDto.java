package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public record ReservationTimeRequestDto(
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, this.startAt());
    }
}
