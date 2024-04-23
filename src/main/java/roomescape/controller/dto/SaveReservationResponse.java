package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record SaveReservationResponse(Long id, String name, String date, ReservationTime time) {

}
