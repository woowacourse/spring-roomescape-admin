package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface TimeDao {
    ReservationTime add(ReservationTime reservationTime);

    Optional<ReservationTime> findById(Long id);

    List<ReservationTime> findAll();

    void delete(Long id);

    boolean isExist(Long id);

    boolean isExist(LocalTime time);
}
