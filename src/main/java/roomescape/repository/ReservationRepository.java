package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();

    Long save(Reservation reservation);

    void deleteById(Long id);
}
