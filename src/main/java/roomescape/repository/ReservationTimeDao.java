package roomescape.repository;

import roomescape.model.ReservationTime;
import roomescape.repository.dto.ReservationTimeSaveDto;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {

    ReservationTime save(final ReservationTimeSaveDto reservationTimeSaveDto);

    Optional<ReservationTime> findById(final Long id);

    List<ReservationTime> findAll();

    boolean deleteById(final Long id);
}
