package roomescape.console.controller;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.console.view.OutputView;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeServiceRequest;
import roomescape.service.dto.ReservationTimeServiceResponse;

@Component
public class ConsoleReservationTimeController {

    private final OutputView outputView;
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationTimeController(OutputView outputView, ReservationTimeService reservationTimeService) {
        this.outputView = outputView;
        this.reservationTimeService = reservationTimeService;
    }

    public void findAll() {
        List<ReservationTimeServiceResponse> reservationTimes = reservationTimeService.findAll();

        outputView.printReservationTimes(reservationTimes);
    }

    public void create(ReservationTimeServiceRequest reservationTimeServiceRequest) {
        ReservationTimeServiceResponse reservationTimeServiceResponse = reservationTimeService.create(
                reservationTimeServiceRequest);

        outputView.printCreateReservationTimeSuccess(reservationTimeServiceResponse);
    }

    public void delete(Long id) {
        reservationTimeService.delete(id);

        outputView.printDeleteReservationTimeSuccess();
    }
}
