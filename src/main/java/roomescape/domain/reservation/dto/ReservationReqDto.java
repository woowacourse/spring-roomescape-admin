package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationReqDto(String name, LocalDate date, LocalTime time) {

    public ReservationReqDto {
        time = time.withNano(0);
    }
}
