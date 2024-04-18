package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(final Reservation reservation);

    void deleteById(final Long reservationId);
}
