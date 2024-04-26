package roomescape.console.controller;

import java.util.List;
import roomescape.console.view.OutputView;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.dto.ReservationTimeServiceRequest;
import roomescape.core.service.dto.ReservationTimeServiceResponse;

public class ConsoleReservationTimeController {

    private final ReservationTimeService reservationTimeService;
    private final OutputView outputView;

    public ConsoleReservationTimeController(ReservationTimeService reservationTimeService, OutputView outputView) {
        this.reservationTimeService = reservationTimeService;
        this.outputView = outputView;
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
