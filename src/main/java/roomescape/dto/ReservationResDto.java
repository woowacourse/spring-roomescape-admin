package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ReservationResDto(Long id, String name, @JsonFormat(pattern = "YYYY-MM-dd") LocalDate date, ReservationTimeResDto time) {

}
