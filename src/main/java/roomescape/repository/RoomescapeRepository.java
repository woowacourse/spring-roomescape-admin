package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface RoomescapeRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean deleteById(long id);
}