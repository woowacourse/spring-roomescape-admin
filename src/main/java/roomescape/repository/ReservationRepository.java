package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;

import java.util.List;

@Repository
public interface ReservationRepository {
    List<Reservation> findAllReservations();

    ReservationResponse insert(ReservationRequest request);

    void deleteById(Long id);
}
