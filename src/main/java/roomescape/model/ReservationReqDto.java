package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationReqDto(String name, LocalDate date, LocalTime time) {

}
