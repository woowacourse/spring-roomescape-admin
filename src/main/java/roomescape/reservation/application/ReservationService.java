package roomescape.reservation.application;

import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.util.List;

public interface ReservationService {

    List<ReservationResponseDto> getReservations();

    ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto);

    void delete(long id);
}
