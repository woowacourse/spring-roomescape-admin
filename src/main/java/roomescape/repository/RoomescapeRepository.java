package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface RoomescapeRepository {

    List<Reservation> findAll();

    Reservation save(final Reservation reservation);

    int deleteById(final long id);
}
