package roomescape.controller.dto.request;


import java.time.LocalDate;

public record ReservationRequest(
        LocalDate date,
        String name,
        Long timeId
) {
}
