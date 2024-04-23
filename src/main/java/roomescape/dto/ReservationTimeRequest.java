package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {
}
