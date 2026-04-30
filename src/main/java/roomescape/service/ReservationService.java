package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

public interface ReservationService {

    List<ReservationResponseDto> readAll();

    ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto);

    void cancel(Long id);
}
