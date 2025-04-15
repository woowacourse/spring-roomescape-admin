package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(LocalDate date, String name, LocalTime time) {

}
