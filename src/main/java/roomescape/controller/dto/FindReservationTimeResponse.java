package roomescape.controller.dto;

import java.time.LocalTime;

public record FindReservationTimeResponse(Long id, LocalTime startAt) {

}
