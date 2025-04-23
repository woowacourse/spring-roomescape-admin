package roomescape.repository.reservationtime;

import java.util.List;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.ReservationTime;

public interface ReservationTimeRepository {

    Long addAndGetId(CreateReservationTimeDto createReservationTimeDto);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
