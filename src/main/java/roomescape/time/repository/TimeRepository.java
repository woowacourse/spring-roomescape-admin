package roomescape.time.repository;

import java.util.List;

import roomescape.reservation.domain.ReservationTime;

public interface TimeRepository {
  List<ReservationTime> findAll();

  ReservationTime save(String startAt);

  ReservationTime findById(long id);

  boolean deleteById(long id);
}
