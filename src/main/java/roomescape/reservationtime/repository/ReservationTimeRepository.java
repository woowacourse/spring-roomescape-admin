package roomescape.reservationtime.repository;

import java.util.List;
import roomescape.reservationtime.model.ReservationTime;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    ReservationTime findById(Long timeId);
}
