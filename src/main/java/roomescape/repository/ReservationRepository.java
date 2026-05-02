package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.controller.ReservationRequest;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(ReservationRequest reservationRequest);

    void deleteById(long id);
}
