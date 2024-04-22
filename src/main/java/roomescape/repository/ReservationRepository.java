package roomescape.repository;

import roomescape.domain.Reservation;
import java.util.List;

public interface ReservationRepository {

    Long save(final Reservation reservation);

    List<Reservation> findAll();

    void deleteById(final Long id);
}
