package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.payload.ReservationRequest;

public interface ReservationRepository {

    Long save(ReservationRequest request);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    void deleteById(Long id);

}
