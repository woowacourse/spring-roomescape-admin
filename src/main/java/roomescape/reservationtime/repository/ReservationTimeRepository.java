package roomescape.reservationtime.repository;

import roomescape.reservation.domain.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservation);
}
