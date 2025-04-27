package roomescape.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.business.ReservationTime;

public record ReservationTimeResponseDto(long id, @JsonFormat(pattern = "hh:mm") LocalTime startAt) {

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }
}
