package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    boolean existsById(long id);

    void deleteById(long id);
}
