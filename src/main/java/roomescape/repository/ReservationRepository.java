package roomescape.repository;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    boolean existsByTimeId(long timeId);

    Reservation save(Reservation reservation);

    void deleteById(long id);
}
