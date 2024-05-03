package roomescape.controller.console;

import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.mapper.ReservationMapper;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import java.util.List;

public class ReservationConsoleController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationConsoleController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationMapper = new ReservationMapper(reservationTimeService);
    }

    public ReservationResponseDto postReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationService.createReservation(reservationMapper.toReservation(reservationRequestDto));
        return ReservationResponseDto.from(reservation);
    }

    public List<ReservationResponseDto> getReservations() {
        return reservationService.findReservations().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public void deleteReservation(Long id) {
        reservationService.removeReservation(id);
    }
}
