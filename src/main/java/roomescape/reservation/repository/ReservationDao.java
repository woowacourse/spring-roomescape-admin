package roomescape.reservation.repository;

import roomescape.reservation.entity.Reservation;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
