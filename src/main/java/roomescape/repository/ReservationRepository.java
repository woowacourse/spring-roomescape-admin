package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.Reservation;
import roomescape.ReservationRequest;

public interface ReservationRepository {

    Optional<Reservation> findById(long id);

    Reservation save(ReservationRequest request);

    boolean removeById(long id);

    List<Reservation> getReservations();
}
