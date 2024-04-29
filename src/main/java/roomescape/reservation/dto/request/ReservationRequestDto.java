package roomescape.reservation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public record ReservationRequestDto(
        @NonNull
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NonNull
        LocalDate date,
        @NonNull
        Long timeId
) {
}
