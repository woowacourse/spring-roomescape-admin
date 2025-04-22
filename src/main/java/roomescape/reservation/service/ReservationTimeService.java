package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;

public interface ReservationTimeService {

    List<ReservationTimeResponseDto> getAll();

    ReservationTimeResponseDto save(ReservationTimeRequestDto requestDto);

    void delete(Long id);
}
