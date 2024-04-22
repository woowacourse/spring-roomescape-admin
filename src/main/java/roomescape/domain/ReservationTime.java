package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTime(Long id,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime startAt) {
}
