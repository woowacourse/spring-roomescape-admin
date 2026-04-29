package roomescape.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReservationRequest (String name, LocalDate date, Long timeId) { }
