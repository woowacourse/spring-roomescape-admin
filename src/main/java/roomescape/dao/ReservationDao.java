package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

public interface ReservationDao {
    List<ReservationEntity> findAll();

    long save(Reservation reservation);

    boolean existsById(long id);

    void deleteById(long id);
}
