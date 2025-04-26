package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

public interface ReservationRepository {

    Reservations findAll();

    Reservation save(Reservation reservation);

    boolean deleteById(Long id);
}
