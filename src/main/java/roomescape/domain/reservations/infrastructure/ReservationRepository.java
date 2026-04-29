package roomescape.domain.reservations.infrastructure;

import java.util.List;
import roomescape.domain.reservations.entity.Reservation;

public interface ReservationRepository {
    int save(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    void deleteById(Long id);
}
