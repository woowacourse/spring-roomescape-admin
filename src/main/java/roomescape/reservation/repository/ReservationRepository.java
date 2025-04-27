package roomescape.reservation.repository;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findByIdOrThrow(Long id);

    Reservation add(Reservation reservation);

    void delete(Long id);
}
