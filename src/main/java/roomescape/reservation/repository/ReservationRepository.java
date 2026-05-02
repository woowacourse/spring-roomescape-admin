package roomescape.reservation.repository;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    List<Reservation> findAll();

    boolean existsById(Long id);

    boolean existsByReservation(String date, Long timeId);
}
