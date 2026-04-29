package roomescape.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;
import roomescape.reservationtime.dto.ReservationTimeResponse;

@Builder
public record ReservationResponse (Long id, String name, LocalDate date, ReservationTimeResponse time) { }
