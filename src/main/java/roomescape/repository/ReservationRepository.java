package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {

    Long add(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    void removeById(Long id);
}
