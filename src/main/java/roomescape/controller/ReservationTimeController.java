package roomescape.controller;

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
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> listReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTimes());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.createReservationTime(request);

        URI location = URI.create("/times/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> createReservationTime(@PathVariable("id") Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }

}
