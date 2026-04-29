package roomescape.time.service;

import java.util.List;

import roomescape.reservation.domain.ReservationTime;

public interface TimeService {
  ReservationTime create(String startAt);

  List<ReservationTime> findAll();

  void deleteById(long id);
}
