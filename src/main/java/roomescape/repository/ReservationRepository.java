package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    ReservationResponse insert(ReservationRequest request);

    void deleteById(Long id);
}
