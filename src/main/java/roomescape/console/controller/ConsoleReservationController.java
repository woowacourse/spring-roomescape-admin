package roomescape.console.controller;

import java.util.List;
import roomescape.console.view.OutputView;
import roomescape.core.service.ReservationService;
import roomescape.core.service.dto.ReservationServiceRequest;
import roomescape.core.service.dto.ReservationServiceResponse;

public class ConsoleReservationController {

    private final ReservationService reservationService;
    private final OutputView outputView;

    public ConsoleReservationController(ReservationService reservationService, OutputView outputView) {
        this.reservationService = reservationService;
        this.outputView = outputView;
    }

    public void findAll() {
        List<ReservationServiceResponse> reservations = reservationService.findAll();

        outputView.printReservations(reservations);
    }

    public void create(ReservationServiceRequest reservationServiceRequest) {
        ReservationServiceResponse reservation = reservationService.create(reservationServiceRequest);

        outputView.printCreateReservationSuccess(reservation);
    }

    public void delete(Long id) {
        reservationService.delete(id);

        outputView.printDeleteReservationSuccess();
    }

}
