package roomescape.controller.console;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;
import roomescape.view.ReservationTimeView;

public class ReservationTimeController {
    private final ReservationTimeView reservationTimeView;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController() {
        reservationTimeView = new ReservationTimeView();
        this.reservationTimeService = new ReservationTimeService();
    }

    public ResponseEntity<ReservationTimeResponse> saveTime() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(
                reservationTimeView.readStartAt()
        );
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.saveTime(reservationTimeRequest);
        reservationTimeView.printSuccessfullyAdded();
        return ResponseEntity.created(URI.create("/times/" + reservationTimeResponse.id()))
                .body(reservationTimeResponse);
    }


}
