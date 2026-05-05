package roomescape.dto;

import java.time.LocalDate;

public record ReservationCreateResponse(Long id, String name, LocalDate date, TimeCreateResponse time) {
}
