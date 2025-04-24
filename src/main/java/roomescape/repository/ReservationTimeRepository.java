package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    public ReservationTime save(ReservationTime reservationTime);

    public List<ReservationTime> findAll();

    public void deleteById(Long id);
}
