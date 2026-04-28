package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDTO(Long id, String name, LocalDate date, LocalTime time) {

}
