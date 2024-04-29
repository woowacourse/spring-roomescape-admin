package roomescape.reservation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReservationRequestDto(
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        Long timeId
) {
}
