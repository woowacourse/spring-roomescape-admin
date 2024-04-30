package roomescape.dao;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationId;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    ReservationId create(Reservation reservation);

    Optional<Reservation> findAnyByTimeId(long timeId);

    Reservation findById(long id);

    List<Reservation> findAll();

    void delete(long id);
}
