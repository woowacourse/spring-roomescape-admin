package roomescape.reservationtime.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservationtime.model.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    Optional<ReservationTime> findById(Long timeId);
}
