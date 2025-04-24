package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation insert(final String name, final LocalDate date, final long timeId);

    void delete(final long id);
}
