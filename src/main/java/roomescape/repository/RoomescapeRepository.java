package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface RoomescapeRepository {

    List<Reservation> findAll();

    Reservation saveReservation(final Reservation reservation);

    void deleteById(final long id);

    void clear();
}
