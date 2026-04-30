package roomescape.domain.reservation.dto.response;

import java.time.LocalDate;
import roomescape.domain.time.domain.Time;

public record ReservationResponseDTO(Long id, String name, LocalDate date, Time time) {

}
