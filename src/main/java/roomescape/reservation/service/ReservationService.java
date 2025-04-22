package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;

public interface ReservationService {

    List<ReservationResponseDto> getAll();

    ReservationResponseDto save(ReservationRequestDto requestDto);

    void delete(Long id);
}
