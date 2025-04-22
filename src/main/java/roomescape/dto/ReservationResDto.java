package roomescape.dto;

import java.time.LocalDate;

public record ReservationResDto(Long id, String name, LocalDate date, ReservationTimeResDto time) {

}
