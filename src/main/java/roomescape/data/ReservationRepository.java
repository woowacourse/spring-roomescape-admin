package roomescape.data;

import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservations findAll();

    void saveAll(Reservations reservations);
}
