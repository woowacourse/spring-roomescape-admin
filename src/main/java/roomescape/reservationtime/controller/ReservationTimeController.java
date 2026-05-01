package roomescape.reservationtime.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.controller.dto.CreateReservationTimeRequest;
import roomescape.reservationtime.controller.dto.ReservationTimeResponse;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private static final String LOCATION_DEFAULT_VALUE = "/times/";

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping()
    public ResponseEntity<ReservationTimeResponse> createReservation(@RequestBody CreateReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.save(request);
        return ResponseEntity.created(URI.create(LOCATION_DEFAULT_VALUE + response.id()))
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTime>> readReservationTimes() {
        List<ReservationTime> reservations = reservationTimeService.findAllReservationTimes();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long reservationTimeId) {
        reservationTimeService.delete(reservationTimeId);
        return ResponseEntity.noContent()
                .build();
    }
}
