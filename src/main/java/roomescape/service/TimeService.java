package roomescape.service;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface TimeService {

    List<ReservationTime> findAllTimes();

    ReservationTime add(ReservationTime time);

    ReservationTime findById(Long id);

    void remove(Long id);
}
