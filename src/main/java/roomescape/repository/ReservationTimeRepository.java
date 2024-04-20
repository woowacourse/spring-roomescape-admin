package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeDto;

public interface ReservationTimeRepository {

    ReservationTime create(ReservationTimeDto reservationTimeDto);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    void deleteById(Long id);

    void deleteAll();
}
