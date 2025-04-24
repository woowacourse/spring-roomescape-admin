package roomescape.database;

import roomescape.domain.reservation.Reservation;

import java.util.List;

public interface ReservationDatabase {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation add(Reservation reservation);

    void delete(Long id);
}
