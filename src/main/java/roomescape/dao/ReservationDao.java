package roomescape.dao;

import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

import java.util.List;

public interface ReservationDao {
    List<ReservationEntity> findAll();

    ReservationEntity save(Reservation reservation);

    boolean existsById(long id);

    void deleteById(long id);
}
