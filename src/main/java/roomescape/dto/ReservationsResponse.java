package roomescape.dto;

import roomescape.domain.Reservation;

import java.util.List;

public record ReservationsResponse(List<Reservation> reservations) {
}
