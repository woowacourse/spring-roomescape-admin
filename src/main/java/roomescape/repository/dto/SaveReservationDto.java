package roomescape.repository.dto;

import java.time.LocalDate;

public record SaveReservationDto(
    String name,
    LocalDate date,
    Long timeSlotId
) {

}
