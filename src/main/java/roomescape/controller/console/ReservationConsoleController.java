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

    public void saveReservation() {
        ReservationRequest reservationRequest = new ReservationRequest(
                reservationView.readName(),
                reservationView.readDate(),
                ReservationTimeView.readReservationTimeIdToDelete(
                        getReservationTimeResponses()
                )
        );
        reservationService.saveReservation(reservationRequest);
        reservationView.printSuccessfullyAdded();
    }

    public ReservationConsoleController(
            final ReservationService reservationService,
            final ReservationTimeService reservationTimeService
    ) {
        this.reservationView = new ReservationView();
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    private List<ReservationTimeResponse> getReservationTimeResponses() {
        return reservationTimeService.getTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteReservation() {
        int reservationId = reservationView.readReservationIdToDelete(getReservationResponses());
        reservationService.deleteReservation(reservationId);
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
