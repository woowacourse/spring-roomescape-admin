package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponseDto;

public interface ReservationService {

    List<ReservationResponseDto> getAll();

    ReservationResponseDto save(ReservationRequest requestDto);

    void delete(Long id);
}
