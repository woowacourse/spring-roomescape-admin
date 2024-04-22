package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    long create(Reservation reservation, long reservationTimeId);

    boolean deleteById(long id);
}
