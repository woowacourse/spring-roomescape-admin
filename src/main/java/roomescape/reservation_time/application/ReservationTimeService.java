package roomescape.reservation_time.application;

import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.ui.dto.ReservationTimeRequestDto;
import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.util.List;

public interface ReservationTimeService {

    List<ReservationTimeResponseDto> getAll();

    ReservationTimeResponseDto create(ReservationTimeRequestDto reservationTimeRequestDto);

    void delete(ReservationTimeId id);
}
