package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationResponseDto (
        Long id,
        String name,
        LocalDate date,
        Time time
){
}
