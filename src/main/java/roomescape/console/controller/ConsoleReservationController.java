package roomescape.console.controller;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.console.view.OutputView;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationServiceResponse;

@Component
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
