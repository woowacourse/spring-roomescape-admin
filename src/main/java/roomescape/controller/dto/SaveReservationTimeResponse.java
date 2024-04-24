package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record SaveReservationTimeResponse(Long id, @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

}
