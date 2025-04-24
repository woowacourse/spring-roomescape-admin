package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public interface ReservationTimeRepository {

    public ReservationTime save(final LocalTime startAt);

    public List<ReservationTime> findAll();

    public void deleteById(Long id);
}
