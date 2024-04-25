package roomescape.reservation.service;

import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ReservationsResponseDto;

public interface ReservationService {

    ReservationsResponseDto findAllReservation();

    ReservationResponseDto addReservation(final ReservationRequestDto request);

    void deleteReservationById(final Long id);
}
