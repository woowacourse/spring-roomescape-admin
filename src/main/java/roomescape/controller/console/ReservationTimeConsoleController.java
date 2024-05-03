package roomescape.controller.console;

import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;
import java.util.List;

public class ReservationTimeConsoleController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeConsoleController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    public ReservationTimeResponseDto postReservationTime(ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeService.createReservationTime(requestDto.toEntity());
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public List<ReservationTimeResponseDto> getReservationTimes() {
        return reservationTimeService.findReservationTimes().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeService.removeReservationTime(id);
    }
}
