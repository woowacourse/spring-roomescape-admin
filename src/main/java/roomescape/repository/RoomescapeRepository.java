package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public interface RoomescapeRepository {

    List<Reservation> findAll();

    Reservation save(final Reservation reservation);

    int deleteById(final long id);

    boolean existsByDateAndTime(final LocalDate date, final ReservationTime time);
}
