package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResDto(Long id, String name, LocalDate date, @JsonFormat(pattern = "HH:mm") LocalTime time) {

}
