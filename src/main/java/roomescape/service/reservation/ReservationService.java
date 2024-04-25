package roomescape.service.reservation;

import roomescape.dto.reservation.request.ReservationRequestDto;
import roomescape.dto.reservation.response.ReservationResponseDto;
import roomescape.dto.reservation.response.ReservationsResponseDto;

public interface ReservationService {

    ReservationsResponseDto findAllReservation();

    ReservationResponseDto addReservation(final ReservationRequestDto request);

    void deleteReservationById(final Long id);
}
