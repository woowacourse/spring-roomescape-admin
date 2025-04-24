package roomescape.reservation.database;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationDatabase {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation add(Reservation reservation);

    void delete(Long id);
}
