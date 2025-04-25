package roomescape.repository.reservation;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationRepository {
    
    List<Reservation> findAll();

    Long addAndGetId(Reservation reservation);

    void deleteById(Long id);

    Reservation findById(Long id);
}
