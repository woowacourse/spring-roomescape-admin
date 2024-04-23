package roomescape.dto;

import roomescape.domain.ReservationTime;

public record CreateReservationResponse(Long id, String name, String date, ReservationTime time) {

}
