package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation insert(final String name, final LocalDate date, final LocalTime time);

    void delete(final long id);
}
