package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.Reservation;
import roomescape.dto.CreateReservationRequest;

public interface ReservationRepository {

    Optional<Reservation> findById(long id);

    long save(CreateReservationRequest request);

    boolean removeById(long id);

    List<Reservation> getReservations();
}
