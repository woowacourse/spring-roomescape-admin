package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ReservationReqDto(
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        long timeId
) {
}
