package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeDao {
    ReservationTime create(String startAt);

    List<ReservationTime> findAll();

    void delete(Long id);

    ReservationTime findById(Long id);
}
