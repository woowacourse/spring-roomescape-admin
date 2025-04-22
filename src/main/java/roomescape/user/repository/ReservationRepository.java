package roomescape.user.repository;

import java.util.List;
import roomescape.user.domain.Reservation;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Reservation getOneById(Long id);

    List<Reservation> findAll();

    void delete(Reservation reservation);
}
