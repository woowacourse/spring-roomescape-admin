package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeDto;

public interface ReservationTimeRepository {

    ReservationTime create(ReservationTimeDto reservationTimeDto);

    List<ReservationTime> findAll();

    ReservationTime findById(Long id);

    void deleteById(Long id);

    void deleteAll();
}
