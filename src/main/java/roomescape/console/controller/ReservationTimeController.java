package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.controller.request.ReservationTimeRequest;
import roomescape.console.controller.response.ReservationTimeResponse;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.response.ReservationTimeResponseDto;

import java.util.List;

@Controller
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTimeResponseDto> reservationTimes = reservationTimeService.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponseDto reservationTime = reservationTimeService.save(reservationTimeRequest.toDto());

        return ReservationTimeResponse.from(reservationTime);
    }

    public void delete(Long id) {
        reservationTimeService.deleteById(id);
    }
}
