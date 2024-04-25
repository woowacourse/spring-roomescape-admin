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
        List<ReservationTimeResponse> reservationTimeResponses = getReservationTimeResponses();
        if (reservationTimeResponses.isEmpty()) {
            reservationView.printHasNotAnyReservationTime();
            return;
        }
        ReservationRequest reservationRequest = new ReservationRequest(
                reservationView.readName(),
                reservationView.readDate(),
                ReservationTimeView.readIndexToReserve(reservationTimeResponses)
        );
        reservationService.saveReservation(reservationRequest);
        reservationView.printSuccessfullyAdded();
    }

    private List<ReservationTimeResponse> getReservationTimeResponses() {
        return reservationTimeService.getTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteReservation() {
        List<ReservationResponse> reservationResponses = getReservationResponses();
        if (reservationResponses.isEmpty()) {
            reservationView.printHasNotAnyReservation();
            return;
        }
        int index = reservationView.readIndexToDelete(reservationResponses);
        reservationService.deleteReservation(reservationResponses.get(index - 1).id());
        reservationView.printSuccessfullyDeleted();
    }

    public void getReservation() {
        reservationView.printReservations(getReservationResponses());
    }

    private List<ReservationResponse> getReservationResponses() {
        return reservationService.getAllReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
