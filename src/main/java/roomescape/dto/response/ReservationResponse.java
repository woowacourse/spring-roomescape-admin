package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ReservationResponse(

        Long id,

        String name,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        TimeResponse time
) {
}
