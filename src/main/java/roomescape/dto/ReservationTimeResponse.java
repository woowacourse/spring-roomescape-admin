package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startAt) {
    public static ReservationTimeResponse from(Long id, LocalTime startAt) {
        return new ReservationTimeResponse(id, startAt);
    }
}
