package roomescape.dto;

import roomescape.entity.Reservation;

public record ReservationRequestDto(
    String name,
    String date,
    String time
) {

}
