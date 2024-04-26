package roomescape.controller.console;

import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ReservationTimeView;
import roomescape.view.ReservationView;

@Controller
public class ReservationConsoleController {
    private final ReservationView reservationView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationConsoleController(
            final ReservationService reservationService,
            final ReservationTimeService reservationTimeService
    ) {
        this.reservationView = new ReservationView();
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void saveReservation() {
        try {
            List<ReservationTimeResponse> reservationTimes = reservationTimeService.findReservationTimes();
            ReservationRequest reservationRequest = new ReservationRequest(
                    reservationView.readName(),
                    reservationView.readDate(),
                    ReservationTimeView.readIndexToReserve(reservationTimes)
            );
            reservationService.saveReservation(reservationRequest);
            reservationView.printSuccessfullyAdded();
        } catch (IllegalStateException exception) {
            reservationView.printHasNotAnyReservationTime();
        }
    }

    public void getReservation() {
        reservationView.printReservations(reservationService.findReservations());
    }

    public void deleteReservation() {
        try {
            List<ReservationResponse> reservationResponses = reservationService.findReservations();
            reservationService.deleteReservation(
                    reservationView.readIndexToDelete(reservationResponses)
            );
            reservationView.printSuccessfullyDeleted();
        } catch (final IllegalStateException exception) {
            reservationView.printHasNotAnyReservation();
        }
    }
}
