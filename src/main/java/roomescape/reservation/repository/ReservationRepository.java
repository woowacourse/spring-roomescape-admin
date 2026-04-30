package roomescape.reservation.repository;

import roomescape.reservation.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    List<Reservation> findAll();
}
