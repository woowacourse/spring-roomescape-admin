package roomescape.dto;

import java.time.LocalDate;

public record ReservationReqDto(String name, LocalDate date, int timeId) {

}
