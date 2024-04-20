package roomescape.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.business.ReservationTimeService;
import roomescape.dto.ReservationTimeSaveRequest;

import java.net.URI;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservationTime(@RequestBody ReservationTimeSaveRequest request) {
        var reservationTime = request.toModel();
        var savedReservationTimeResponse = reservationTimeService.create(reservationTime);
        return ResponseEntity.created(URI.create("/times/" + savedReservationTimeResponse.id())).build();
    }
}
