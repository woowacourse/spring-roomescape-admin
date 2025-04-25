package roomescape.persistence.repository.reservationtime;

import java.util.List;
import roomescape.entity.ReservationTime;
import roomescape.presentation.dto.CreateReservationTimeDto;

public interface ReservationTimeRepository {

    Long addAndGetId(CreateReservationTimeDto createReservationTimeDto);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
