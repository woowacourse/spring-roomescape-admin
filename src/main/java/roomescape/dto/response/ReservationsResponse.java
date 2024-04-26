package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.util.List;

public record ReservationsResponse(List<Reservation> reservations) {
}
