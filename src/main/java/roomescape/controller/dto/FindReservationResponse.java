package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record FindReservationResponse(Long id, String name, String date, ReservationTime time) {

}
