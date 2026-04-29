package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(ReservationRequest reservation);

    void delete(long id);
}
