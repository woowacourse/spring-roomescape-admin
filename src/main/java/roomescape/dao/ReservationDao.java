package roomescape.dao;

import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

import java.util.List;

public interface ReservationDao {
    List<ReservationEntity> findAll();

    long save(Reservation reservation);

    boolean existsById(long id);

    void deleteById(long id);
}
