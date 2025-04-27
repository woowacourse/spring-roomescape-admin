package roomescape.reservationTime.repository;

import roomescape.reservationTime.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime add(ReservationTime reservationTime);

    ReservationTime findByIdOrThrow(Long id);

    void delete(Long id);

    Long insertWithKeyHolder(ReservationTime reservationTime);
}
