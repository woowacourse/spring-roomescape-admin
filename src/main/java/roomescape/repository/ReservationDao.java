package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation insert(Reservation reservation);

    void deleteById(long reservationId);

    Reservation findById(long reservationId);
}
