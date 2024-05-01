package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.controller.request.ReservationRequest;
import roomescape.console.controller.response.ReservationResponse;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.request.ReservationRequestDto;
import roomescape.core.service.response.ReservationResponseDto;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationResponse> findAll() {
        List<ReservationResponseDto> reservationResponses = reservationService.findAll();

        return reservationResponses.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationRequestDto reservation = reservationRequest.toDto();

        ReservationResponseDto reservationResponse = reservationService.save(reservation);

        return ReservationResponse.from(reservationResponse);
    }

    public void delete(Long id) {
        reservationService.deleteById(id);
    }
}
