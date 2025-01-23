package roomescape.repository;

import roomescape.domain.Reservation;

public record ReservationWithRank(Reservation reservation, long rank) {
}
