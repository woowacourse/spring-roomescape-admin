package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeRequestDto(@JsonFormat(pattern = "HH:mm") LocalTime startAt) {

}
